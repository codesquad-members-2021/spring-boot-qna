package com.codessquad.qna.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registry.addViewController("/join").setViewName("user/form");
        registry.addViewController("/login").setViewName("user/login");
        registry.addViewController("/loginFailed").setViewName("user/login_failed");
    }

    @Bean
    public Docket api() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("Pyro API")
                .description("Spring API made by Pyro")
                .license("Apache License Version 2.0")
                .version("0.1")
                .build();

        // http://localhost:8080/swagger-ui.html
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("pyro-api")
                .apiInfo(apiInfo)
                .select()
                .paths(PathSelectors.ant("/api/**"))
                .build();
    }
}
