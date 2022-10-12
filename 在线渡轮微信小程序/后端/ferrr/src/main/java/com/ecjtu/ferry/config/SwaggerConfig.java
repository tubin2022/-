package com.ecjtu.ferry.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket getRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                // 扫描自定义控制器所在的包路径
                .apis(RequestHandlerSelectors.basePackage("com.ecjtu.ferry.controller"))
                .paths(PathSelectors.any())
                // 构建swagger首页的自定义信息
                .build().apiInfo(new ApiInfoBuilder()
                        .title("在线轮渡微信小程序")
                        .description("后台接口管理")
                        .version("1.0")
                        .contact(new Contact("第二组", "http://blog.csdn.net", "280896352@qq.com"))
                        .license("The Apache License")
                        .licenseUrl("https://www.apache.org/")
                        .build());
    }
}
