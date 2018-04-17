package com.fly.common.xss;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * XSS 过滤处理(装饰设计模式)
 * <p>
 * @author liufei
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    /**
     * 没被包装过的request,特殊场景需要自己过滤
     */
    HttpServletRequest orgRequest;

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        orgRequest = request;
    }

    /**
     * 拿到没被包装的request
     * @param request
     * @return
     */
    public static HttpServletRequest getOrgRequest(HttpServletRequest request) {
        if (request instanceof XssHttpServletRequestWrapper) {
            return ((XssHttpServletRequestWrapper) request).getOrgRequest();
        }
        return request;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        //非JSON数据直接返回
        if (!MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(super.getHeader(HttpHeaders.CONTENT_TYPE))) {
            return super.getInputStream();
        }
        //为空直接返回
        String json = IOUtils.toString(super.getInputStream(), "utf-8");
        if (StringUtils.isBlank(json)) {
            return super.getInputStream();
        }
        //Xss过滤
        json = xssEncode(json);
        ByteArrayInputStream bis = new ByteArrayInputStream(json.getBytes("utf-8"));

        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return true;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return bis.read();
            }
        };
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(xssEncode(name));
        if (StringUtils.isNotBlank(value)) {
            value = xssEncode(value);
        }
        return value;
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(xssEncode(name));
        if (StringUtils.isNotBlank(value)) {
            value = xssEncode(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(xssEncode(name));
        if (values == null || values.length == 0) {
            return null;
        }
        for (int i = 0; i < values.length; i++) {
            values[i] = xssEncode(values[i]);
        }
        return values;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> map = new LinkedHashMap<>();
        Map<String, String[]> parameters = super.getParameterMap();

        Iterator<Map.Entry<String, String[]>> iterator = parameters.entrySet().iterator();
        while (iterator.hasNext()){
            String[] values = iterator.next().getValue();
            for (int i = 0; i < values.length; i++) {
                values[i] = xssEncode(values[i]);
            }
            map.put(iterator.next().getKey(), values);
        }
        return map;
    }

    /**
     * 下面两方法用于获取原始的request对象
     */
    public HttpServletRequest getOrgRequest() {
        return orgRequest;
    }

    /**
     * 暂时不过滤
     * @param input
     * @return
     */
    private String xssEncode(String input) {
        return input;
    }

}
