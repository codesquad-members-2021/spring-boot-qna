package com.codessquad.qna.repository;

import com.codessquad.qna.entity.User;

import java.util.List;

public interface UserRepository {

    public void save(User user);

    public List<User> getUsers();

    public User getUser(int id);
}
