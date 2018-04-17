package com.fly.modules.sys.oauth2;

import com.fly.common.utils.HttpContextUtils;
import com.fly.common.utils.Rr;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author : liufei on 2018/4/9
 */
public class OAuth2Filter extends AuthenticatingFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        //获取请求token，如果token不存在，直接返回401
        String token = getRequestToken((HttpServletRequest) servletRequest);
        if (StringUtils.isBlank(token)){
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());

            String result = new Gson().toJson(Rr.error(HttpStatus.SC_UNAUTHORIZED,"invalid token"));
            response.getWriter().print(result);
            return false;
        }
        return executeLogin(servletRequest,servletResponse);
    }

    /**
     * 验证token
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        String token = getRequestToken((HttpServletRequest) servletRequest);
        if (StringUtils.isBlank(token)){
            return null;
        }
        return new OAuth2Token(token);
    }

    /**
     * 成功
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (((HttpServletRequest)request).getMethod().equals(RequestMethod.OPTIONS.name())){
            return true;
        }
        return false;
    }

    /**
     * 失败
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());

        Throwable throwable = e.getCause()==null?e:e.getCause();
        Rr result = Rr.error(HttpStatus.SC_UNAUTHORIZED,throwable.getMessage());
        String json = new Gson().toJson(result);
        try {
            httpResponse.getWriter().print(json);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    public String getRequestToken(HttpServletRequest request){
        //从head中获取token
        String token = request.getHeader("token");
        //head中不存在，从参数中获取
        if (StringUtils.isBlank(token)){
            token = request.getParameter("token");
        }
        return token;
    }
}
