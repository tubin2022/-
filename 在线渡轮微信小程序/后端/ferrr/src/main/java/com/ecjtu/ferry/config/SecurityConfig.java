package com.ecjtu.ferry.config;

import com.ecjtu.ferry.handler.UserAuthenticationEntryPoint;
import com.ecjtu.ferry.handler.UserAuthenticationFailureHandler;
import com.ecjtu.ferry.handler.UserAuthenticationSuccessHandler;
import com.ecjtu.ferry.handler.UserLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsUtils;

/**
 * 安全框架springSecurity配置
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    @Qualifier("userServiceImpl")
    private UserDetailsService userDetailsService;
    @Autowired
    private UserAuthenticationEntryPoint userAuthenticationEntryPoint;
    @Autowired
    private UserAuthenticationFailureHandler userAuthenticationFailureHandler;
    @Autowired
    private UserAuthenticationSuccessHandler userAuthenticationSuccessHandler;
    @Autowired
    private UserLogoutSuccessHandler userLogoutSuccessHandler;

    /**
     * 重写配置方法 实现自定义配置 注意：有三个同名方法 重写参数为HttpSecurity
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭csrf和frameOptions 不关会映射前端请求
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.cors();//开启跨域请求
        //默认所有的资源都需要登录后才能访问
        http.authorizeRequests()//请求授权
                //设置允许前端跨域设置
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
//                 .antMatchers("/login","/user/registe","/swagger-ui.html").permitAll() //放行login
                 .antMatchers("/**").permitAll() //放行login
                .anyRequest().authenticated()//其他请求需要身份认证
                .and().formLogin()  //自定义登录处理器
                .successHandler(userAuthenticationSuccessHandler)//注册登录成功处理
                .failureHandler(userAuthenticationFailureHandler)//注册登录失败处理器
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(userAuthenticationEntryPoint)//注册匿名登录处理器
                .and().logout().permitAll()//放行logout
                .logoutSuccessHandler(userLogoutSuccessHandler);//注册退出登录处理器

    }

    /**
     * 放行swagger
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // allow Swagger URL to be accessed without authentication
        web.ignoring().antMatchers(
                "/swagger-ui.html",
                "/v2/api-docs", // swagger api json
                "/swagger-resources/configuration/ui", // 用来获取支持的动作
                "/swagger-resources", // 用来获取api-docs的URI
                "/swagger-resources/configuration/security", // 安全选项
                "/swagger-resources/**",
                "/webjars/springfox-swagger-ui",
                "/webjars/springfox-swagger-ui/**"
        );
    }

    /**
     * 获取密码加密器 放入spring容器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 身份验证组件  把身份验证组件交个spring容器管理
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * 密码验证
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
