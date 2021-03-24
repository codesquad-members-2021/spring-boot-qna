package com.codessquad.qna.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String userId);// @@@@@@@@@@ 이걸 옵셔널 리턴으로 변경하고 싶은데 자꾸 빌드에러가 떠요.. 구글링중입니다.. ㅠㅠ
    //Optional findByUserId(String userId);

    //Optional<User> findByUserId(String userId);
    //리턴타입 1 user
    //리턴타입 2 optional
}
