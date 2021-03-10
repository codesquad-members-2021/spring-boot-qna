package com.codessquad.qna;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import java.util.EnumSet;

@EnableJpaRepositories
@EnableJpaAuditing
@SpringBootApplication
public class QnaApplication implements WebApplicationInitializer {

    public static void main(String[] args) {
        SpringApplication.run(QnaApplication.class, args);
    }

    @Override
    public void onStartup(ServletContext servletContext) {
        servletContext.addFilter("httpMethodFilter", HiddenHttpMethodFilter.class)
                .addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, "/*");
    }
}
