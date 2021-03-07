package com.codessquad.qna.repository;


import com.codessquad.qna.domain.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryUserRepository implements UserRepository {

    private final Map<String, User> userMap = Collections.synchronizedMap(new HashMap<>());


    @Override
    public void save(User user) {
        validateUserDuplication(user);
        userMap.put(user.getUserId(), user);
    }

    @Override
    public void updateUserData(User user) {
        userMap.put(user.getUserId(), user);
    }

    private void validateUserDuplication(User user1) {
        for (User user2 : userMap.values()) {
            if (user1.equals(user2)) {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            }
        }
    }

    @Override
    public List<User> findUserALl() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public User findUserByUserID(String userId) {
        return userMap.get(userId);
    }

}
