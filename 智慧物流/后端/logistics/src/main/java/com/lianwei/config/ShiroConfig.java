package com.lianwei.config;

import com.lianwei.filter.ShiroFilter;
import com.lianwei.util.ShiroRealm;
import org.apache.shiro.authc.credential.Md5CredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * shiro的配置文件
 */
@Configuration
public class ShiroConfig {
    /**
     * 配置shiro中的securityManager，这里使用的是DefaultWebSecurityManager
     * @param shiroRealm
     * @return
     */
    @Bean
    public SecurityManager securityManager(@Qualifier("shiroRealm") ShiroRealm shiroRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm);
        return securityManager;
    }

    /**
     * 配置域Realm，这里配置的是自定义的一个realm，用来验证授权
     * @param matcher
     * @return
     */
    @Bean
    public ShiroRealm shiroRealm(@Qualifier("matcher") Md5CredentialsMatcher matcher){
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCredentialsMatcher(matcher);
        return shiroRealm;
    }

    /**
     * 配置密码比对器
     * @return
     */
    @Bean
    public Md5CredentialsMatcher matcher(){
        Md5CredentialsMatcher matcher = new Md5CredentialsMatcher();
        matcher.setHashIterations(64);
        return matcher;
    }

    /**
     * 配置shiro的过滤器，这里是返回ShiroFilterFactoryBean中getObject方法的类型
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String,String> filteMap = new HashMap<>();
        filteMap.put("/user/login","anon");
        filteMap.put("/user/registe","anon");
        filteMap.put("/docs", "anon");
        filteMap.put("/swagger-ui.html", "anon");
        filteMap.put("/webjars/springfox-swagger-ui/**", "anon");
        filteMap.put("/swagger-resources/**", "anon");
        filteMap.put("/v2/api-docs", "anon");
        filteMap.put("/icon/**","anon");
        filteMap.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filteMap);
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        filters.put("authc",new ShiroFilter());
        return shiroFilterFactoryBean;
    }
}
