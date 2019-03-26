package com.suntion.config;

import com.suntion.auth.service.realm.AccountPasswordAuthorizingRealm;
import com.suntion.auth.service.realm.TelCheckCodeAuthorizingRealm;
import com.suntion.core.jwt.JwtAccessControlFilterFilter;
import com.suntion.core.jwt.JwtAuthorizingRealm;
import com.suntion.core.jwt.SuntionModularRealmAuthenticator;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Suntion
 */
@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //设置拦截器
        Map<String, Filter> filters = new LinkedHashMap<>();
        //无状态过滤
        filters.put("jwt", new JwtAccessControlFilterFilter());
        shiroFilterFactoryBean.setFilters(filters);

        //配置访问权限  拦截策略以键值对存入map
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/druid/**", "anon");
        filterChainDefinitionMap.put("/unauth/**", "anon");
        filterChainDefinitionMap.put("/**/*.stream", "anon");
        filterChainDefinitionMap.put("/**", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setAuthenticator(suntionModularRealmAuthenticator());

        List<Realm> realms = new ArrayList<>();
        realms.add(telCheckCodeAuthorizingRealm());
        realms.add(accountPasswordAuthorizingRealm());
        realms.add(jwtAuthorizingRealm());
        securityManager.setRealms(realms);
        return securityManager;
    }

    /**
     * 系统自带的Realm管理，主要针对多realm
     */
    @Bean
    public SuntionModularRealmAuthenticator suntionModularRealmAuthenticator() {
        SuntionModularRealmAuthenticator modularRealmAuthenticator = new SuntionModularRealmAuthenticator();
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return modularRealmAuthenticator;
    }

    @Bean
    public JwtAuthorizingRealm jwtAuthorizingRealm() {
        return new JwtAuthorizingRealm();
    }

    @Bean
    public AccountPasswordAuthorizingRealm accountPasswordAuthorizingRealm() {
        return new AccountPasswordAuthorizingRealm();
    }

    @Bean
    public TelCheckCodeAuthorizingRealm telCheckCodeAuthorizingRealm() {
        return new TelCheckCodeAuthorizingRealm();
    }

}
