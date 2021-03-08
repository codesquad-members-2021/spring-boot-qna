package com.codessquad.qna.web.users;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUserId(String userId);
}
