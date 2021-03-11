package com.codessquad.qna.web.repository;

import com.codessquad.qna.web.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
