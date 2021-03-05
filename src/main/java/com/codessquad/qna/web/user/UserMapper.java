package com.codessquad.qna.web.user;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserMapper {
    private Map<String, User> mapper = new LinkedHashMap<>();

    public void add(User user) {
        mapper.put(user.getUserId(), user);
    }

    public List<User> getUsers() {
        return mapper.entrySet().
                stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public User getUser(String userId) {
        // TODO: 존재하지 않는 userId 로 요청했을 때에 대한 예외처리 필요
        return mapper.getOrDefault(userId, new User());
    }
}
