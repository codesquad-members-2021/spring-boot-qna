package com.codessquad.qna.service;


import com.codessquad.qna.repository.MemoryUserRepository;
import com.codessquad.qna.repository.UserRepostiory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private final UserRepostiory userRepostiory;

    public SpringConfig(UserRepostiory userRepostiory) {
        this.userRepostiory = userRepostiory;
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepostiory);
    }

    @Bean
    public UserRepostiory userRepostiory() {
        return new MemoryUserRepository();
    }
}
