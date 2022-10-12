package com.ecjtu.ferry.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
   @Value("${upload.file.location}")
   private String fileLocation;
   @Value("${upload.file.path}")
   private String filePath;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //添加映射
        registry.addMapping("/**")
                .allowCredentials(true)//是否发现cookie
                .allowedOriginPatterns("*")//设置放行哪些原始域
                .allowedMethods(new String[]{"GET","POST"})//设置放行哪些请求方式
                .allowedHeaders("*")//设置放行哪些原始请求头信息
                .exposedHeaders("*");//暴露哪些原始请求头信息
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(filePath).addResourceLocations(fileLocation);
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
