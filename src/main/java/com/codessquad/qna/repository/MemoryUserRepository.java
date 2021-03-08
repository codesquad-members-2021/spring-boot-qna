package com.codessquad.qna.repository;


import com.codessquad.qna.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryUserRepository implements UserRepository {

    private final Map<String, User> userMap = new ConcurrentHashMap<>();

    @Override
    public User save(User user) {
        userMap.put(user.getUserId(), user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public boolean existsUserByUserId(String userId) {
        if (userMap.get(userId) == null) {
            return false;
        }
        return true;
    }

    @Override
    public User findUserById(Long id) {
        for (User user : userMap.values()) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User findUserByUserId(String userId) {
        return userMap.get(userId);
    }

}
