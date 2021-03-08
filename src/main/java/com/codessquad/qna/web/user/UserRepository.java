package com.codessquad.qna.web.user;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class UserRepository {
    private Map<String, User> users = new ConcurrentHashMap<>();

    public void add(User user) {
        users.put(user.getUserId(), user);
    }

    public void update(String userId, User user) {
        users.put(userId, user);
    }

    public List<User> getUsers() {
        return users.entrySet().
                stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public User getUser(String userId) {
        // TODO: 존재하지 않는 userId 로 요청했을 때에 대한 예외처리 필요
        return users.getOrDefault(userId, new User());
    }
}
