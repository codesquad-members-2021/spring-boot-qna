package com.codessquad.qna.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/users/form").setViewName("user/form");
        registry.addViewController("/users/login").setViewName("user/login");
        registry.addViewController("/questions/form").setViewName("qna/form");
    }
}