package com.ecjtu.ferry.controller;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.collect.Sets;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.OperationBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.service.Operation;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ApiListingScannerPlugin;
import springfox.documentation.spi.service.contexts.DocumentationContext;
import springfox.documentation.spring.web.readers.operation.CachingOperationNameGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 将安全框架SpringSecurity默认封装的登录、注销接口加入到swagger中
 */
@Component
public class SpringSecurityApi implements ApiListingScannerPlugin {
    @Override
    public List<ApiDescription> apply(DocumentationContext documentationContext) {
        //1、添加springSecurity登录接口到swagger中
        Parameter phone = new ParameterBuilder()
                .name("username")
                .description("手机号码")
                .type(new TypeResolver().resolve(String.class))
                .modelRef(new ModelRef("string"))
                .parameterType("form")
                .build();
        Parameter password = new ParameterBuilder()
                .name("password")
                .description("密码")
                .type(new TypeResolver().resolve(String.class))
                .modelRef(new ModelRef("string"))
                .parameterType("form")
                .build();
        Operation login = new OperationBuilder(new CachingOperationNameGenerator())
                .method(HttpMethod.POST)
                .summary("登录")
                .tags(Sets.newHashSet("登录相关接口"))
                .responseMessages(Sets.newHashSet(new ResponseMessageBuilder().code(200).message("OK").build()))
                .consumes(Sets.newHashSet(MediaType.MULTIPART_FORM_DATA_VALUE))
                .produces(Sets.newHashSet(MediaType.APPLICATION_JSON_VALUE))
                .parameters(Arrays.asList(phone,password))
                .build();
        ApiDescription loginDesc = new ApiDescription(null,"/login","登录",Arrays.asList(login),false);

        //退出登录接口
        Operation logout = new OperationBuilder(new CachingOperationNameGenerator())
                .method(HttpMethod.GET)
                .summary("注销")
                .notes("退出登录")
                .tags(Sets.newHashSet("登录相关接口"))
                .responseMessages(Sets.newHashSet(new ResponseMessageBuilder().code(200).message("OK").build()))
                .build();
        ApiDescription logoutDesc = new ApiDescription(null,"/logout","注销",Arrays.asList(logout),false);
        documentationContext.getTags().add(new Tag("登录相关接口","登录，注销"));
        List<ApiDescription> apiDescriptionList = new ArrayList<>(Arrays.asList(loginDesc,logoutDesc));
        return apiDescriptionList;
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return DocumentationType.SWAGGER_2.equals(documentationType);
    }
}
