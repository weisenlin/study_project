package com.wsl.study.controller;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

/**
 * shiro的配置类
 *
 * @author Administrator
 */
@Configuration
public class ShiroConfiguration {


    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);
        //配置登录的url和登录成功的url
        bean.setLoginUrl("/");
        bean.setSuccessUrl("/layout.do");
        //配置访问权限
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/*.do", "authc");//表示需要认证才可以访问
        filterChainDefinitionMap.put("/*.*", "authc");
        filterChainDefinitionMap.put("/logout*", "anon");
        filterChainDefinitionMap.put("ajaxLogin.do","anon");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }

    //配置核心安全事务管理器
    @Bean(name = "securityManager")
    public SecurityManager securityManager(@Qualifier("myRealm") MyRealm authRealm) {
        System.err.println("--------------shiro已经加载----------------");
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(authRealm);
        return manager;
    }

    //配置自定义的权限登录器
    @Bean(name = "myRealm")
    public MyRealm authRealm() {
        MyRealm myRealm = new MyRealm();
//        myRealm.setCredentialsMatcher(matcher);
        return myRealm;
    }

    //    //配置自定义的密码比较器
//    @Bean(name="credentialsMatcher")
//    public CredentialsMatcher credentialsMatcher() {
//        return new CredentialsMatcher();
//    }
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}