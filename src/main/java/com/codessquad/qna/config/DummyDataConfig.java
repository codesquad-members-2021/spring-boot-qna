package com.codessquad.qna.config;

import com.codessquad.qna.common.DummyDataFactory;
import com.codessquad.qna.answer.AnswerRepository;
import com.codessquad.qna.question.QuestionRepository;
import com.codessquad.qna.user.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DummyDataConfig {
    @Bean
    public ApplicationRunner addUserDummyData(UserRepository userRepository) {
        return args -> userRepository.saveAll(DummyDataFactory.createUsers());
    }

    @Bean
    public ApplicationRunner addQuestionDummyData(QuestionRepository questionRepository) {
        return args -> questionRepository.saveAll(DummyDataFactory.createQuestions());
    }

    @Bean
    public ApplicationRunner addAnswerDummyData(AnswerRepository answerRepository) {
        return args -> answerRepository.saveAll(DummyDataFactory.createAnswers());
    }
}
