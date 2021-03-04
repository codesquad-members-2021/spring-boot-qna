package com.codessquad.qna.repository;

import com.codessquad.qna.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    public void save(User user);

    public List<User> findAll();

    public Optional<User> find(String userId);

    public void remove(String userId);

    public int size();
}
