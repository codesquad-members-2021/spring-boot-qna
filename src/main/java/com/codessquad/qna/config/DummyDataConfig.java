package com.codessquad.qna.config;

import com.codessquad.qna.question.Answer;
import com.codessquad.qna.question.AnswerRepository;
import com.codessquad.qna.question.Question;
import com.codessquad.qna.question.QuestionRepository;
import com.codessquad.qna.user.User;
import com.codessquad.qna.user.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DummyDataConfig {
    @Bean
    public ApplicationRunner addUserDummyData(UserRepository userRepository) {
        return args -> userRepository.saveAll(User.getDummyData());
    }

    @Bean
    public ApplicationRunner addQuestionDummyData(QuestionRepository questionRepository) {
        return args -> questionRepository.saveAll(Question.getDummyData());
    }

    @Bean
    public ApplicationRunner addAnswerDummyData(AnswerRepository answerRepository) {
        return args -> answerRepository.saveAll(Answer.getDummyData());
    }
}
