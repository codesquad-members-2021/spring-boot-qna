package com.codessquad.qna.repository;


import com.codessquad.qna.domain.User;
import java.util.ArrayList;
import java.util.List;

public class MemoryUserRepository implements UserRepository {

    private List<User> users = new ArrayList<>();


    @Override
    public void save(User user) {
        validateUserDuplication(user);
        users.add(user);
    }

    private void validateUserDuplication(User user1) {
        for (User user2 : users) {
            if (isMatchingUserId(user1, user2)) {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            }
        }
    }

    private boolean isMatchingUserId(User user1, User user2) {
        return user1.getUserId().equals(user2.getUserId());
    }

    @Override
    public List<User> findUserALl() {
        return users;
    }
}
