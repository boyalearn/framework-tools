package com.framework.tool.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(false)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                //不显示错误的接口地址
                .paths(PathSelectors.regex("/error.*").negate())
                .paths(PathSelectors.regex("/.*"))// 对根下所有路径进行监控
                .build();

    }

    /**
     * 创建该Api的基本信息（这些基本信息会展现在文档页面中）
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(" app项目接口文档")
                .termsOfServiceUrl("www.baidu.com")
                .version("1.0")
                .build();
    }
}
