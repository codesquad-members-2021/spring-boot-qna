package com.codessquad.qna.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class MyConfig implements WebMvcConfigurer {
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("spring-boot-qna")
                .description("spring-boot-qna")
                .build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("spring-boot-qna")
                .apiInfo(this.apiInfo())
                .select()
                .paths(PathSelectors.ant("/api/**"))
                .build();
    }
}
