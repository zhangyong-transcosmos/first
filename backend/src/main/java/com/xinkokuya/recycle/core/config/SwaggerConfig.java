package com.xinkokuya.recycle.core.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xinkokuya.recycle.core.ProjectConstant;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket defaultApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage(ProjectConstant.CONTROLLER_PACKAGE))
                .paths(PathSelectors.ant("login")).build().globalOperationParameters(getParameters())
                .apiInfo(apiInfo("回收系统接口文档", "回收系统接口说明&调试", "1.0"));
    }

    private ApiInfo apiInfo(String title, String description, String version) {
        return new ApiInfoBuilder().title(title).description(description).version(version).build();
    }

    private List<Parameter> getParameters() {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder.parameterType("header").name("Authorization").description("jwt-token")
                .modelRef(new ModelRef("string")).required(true).build();
        List<Parameter> parameters = new ArrayList<Parameter>();
        parameters.add(parameterBuilder.build());
        return parameters;
    }
}