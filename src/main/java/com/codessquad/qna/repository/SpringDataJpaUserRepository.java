package com.codessquad.qna.repository;

import com.codessquad.qna.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaUserRepository extends JpaRepository<User, Long>, UserRepository {

    @Override
    User save(User user);

}
