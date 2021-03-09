package com.codessquad.qna.web.repository;

import com.codessquad.qna.web.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
