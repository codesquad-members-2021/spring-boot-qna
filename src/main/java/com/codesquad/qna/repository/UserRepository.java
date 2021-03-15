package com.codesquad.qna.repository;

import com.codesquad.qna.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
