package com.scd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengdu
 * @date 2019/6/29.
 */
@Configuration
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.scd.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(createParamList());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("File Service")
                .description("file upload download")
                .contact(new Contact("chengdu","","3281328128@qq.com"))
                .license("Apache License 2.0")
                .version("0.0.1")
                .build();
    }

    private List<Parameter> createParamList() {
        // 添加请求头参数
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        List<Parameter> parameterList = new ArrayList<>();
        parameterBuilder.name("db-code").description("datasource router")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        parameterList.add(parameterBuilder.build());
        return parameterList;
    }
}
