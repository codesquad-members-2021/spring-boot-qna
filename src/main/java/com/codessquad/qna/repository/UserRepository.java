package com.codessquad.qna.repository;

import com.codessquad.qna.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    public void save(User user);

    public List<User> findAll();

    public Optional<User> findById(Long id);

    public void update(User oldUserInfo, User updateUserInfo);

    public void remove(Long id);

    public int size();

}
