package com.codessquad.qna;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry){
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

        registry.addViewController("/users/signUp").setViewName("user/form");
        registry.addViewController("/user/login").setViewName("user/login");
        registry.addViewController("/questions/form").setViewName("qna/form");
    }
}
