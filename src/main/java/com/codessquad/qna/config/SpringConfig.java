package com.codessquad.qna.config;


import com.codessquad.qna.repository.JpaUserRepository;
import com.codessquad.qna.repository.UserRepository;
import com.codessquad.qna.service.UserService;
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
    public UserRepository userRepostiory() {
        return new JpaUserRepository(entityManager);
    }
}
