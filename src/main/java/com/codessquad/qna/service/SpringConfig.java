package com.codessquad.qna.service;


import com.codessquad.qna.repository.JpaUserRepository;
import com.codessquad.qna.repository.MemoryUserRepository;
import com.codessquad.qna.repository.UserRepostiory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class SpringConfig {
    private final EntityManager entityManager;

    public SpringConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepostiory());
    }

    @Bean
    public UserRepostiory userRepostiory() {
        return new JpaUserRepository(entityManager);
    }
}
