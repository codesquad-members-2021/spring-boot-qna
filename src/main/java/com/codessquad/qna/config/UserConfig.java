package com.codessquad.qna.config;

import com.codessquad.qna.repository.MemoryUserRepository;
import com.codessquad.qna.repository.UserRepository;
import com.codessquad.qna.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public UserService userService() {
        return new UserService(userRepository());
    }

    @Bean
    public UserRepository userRepository() {
        return new MemoryUserRepository();
    }
}
