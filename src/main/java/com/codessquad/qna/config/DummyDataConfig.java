package com.codessquad.qna.config;

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
}
