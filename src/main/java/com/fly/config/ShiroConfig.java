package com.fly.config;

import com.fly.modules.sys.oauth2.OAuth2Filter;
import com.fly.modules.sys.oauth2.OAuth2Realm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author : liufei on 2018/4/9
 */
@Configuration
public class ShiroConfig {

    @Bean(name = "sessionManager")
    public SessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdCookieEnabled(true);
        return sessionManager;
    }

    @Bean("securityManager")
    public SecurityManager securityManager(OAuth2Realm oAuth2Realm, SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(oAuth2Realm);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean shirFilter = new ShiroFilterFactoryBean();
        shirFilter.setSecurityManager(securityManager);

        //过滤
        Map<String,Filter> oAuth2FilterMap = new HashMap<>();
        oAuth2FilterMap.put("oauth2",new OAuth2Filter());
        shirFilter.setFilters(oAuth2FilterMap);
        //添加过滤路径
        Map<String,String> urlMap = new HashMap<>();
        urlMap.put("/webjars/**", "anon");
        urlMap.put("/druid/**", "anon");
        urlMap.put("/app/**", "anon");
        urlMap.put("/sys/login", "anon");
        urlMap.put("/swagger/**", "anon");
        urlMap.put("/v2/api-docs", "anon");
        urlMap.put("/swagger-ui.html", "anon");
        urlMap.put("/swagger-resources/**", "anon");
        urlMap.put("/captcha.jpg", "anon");
        urlMap.put("/**", "oauth2");

        shirFilter.setFilterChainDefinitionMap(urlMap);
        return shirFilter;
    }

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }


}
