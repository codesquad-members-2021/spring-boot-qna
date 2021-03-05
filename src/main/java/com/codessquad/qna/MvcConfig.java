package com.codessquad.qna;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

        // URL과 html 쉽게 연결하기
        // 컨트롤러 클래스에 GetMapping 에노테이션 메소드를 만드는 것과 큰차이가 없는 것 같다..
        // 실험을 통해 알아낸 것: 정보를 주고받지 않는 일반적인 GET 통신일 경우에만 이 뷰컨트롤러를 사용할 수 있는 것 같다.
        // EX) index
        registry.addViewController("/user/form.html").setViewName("user/form");
    }
}
