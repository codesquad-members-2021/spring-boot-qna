package com.codessquad.qna.config;

import com.codessquad.qna.repository.MemoryQnaRepository;
import com.codessquad.qna.repository.QnaRepository;
import com.codessquad.qna.service.QnaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QnaConfig {

    @Bean
    public QnaService qnaService() {
        return new QnaService(qnaRepository());
    }

    @Bean
    public QnaRepository qnaRepository() {
        return new MemoryQnaRepository();
    }
}
