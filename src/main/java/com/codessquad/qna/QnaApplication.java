package com.codessquad.qna;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableJpaAuditing
@SpringBootApplication
public class QnaApplication {

	public static void main(String[] args) {
		SpringApplication.run(QnaApplication.class, args);
	}

}
