package com.codessquad.qna.domain;

import com.codessquad.qna.utils.ValidUtils;

import javax.persistence.*;

@Entity //m 데이타베이스의 테이블과 일대일로 매칭되는 객체 단위
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //m 키 생성 전략 : 기본 키 생성을 데이터베이스에 위임, 새로운 레코드가 생성이 될때마다 마지막 PK 값에서 자동으로 +1
    private Long primaryKey;

    @Column(nullable = false, length = 20)
    private String userId;
    @Column(nullable = false, length = 20)
    private String password;
    private String newPassword;
    @Column(nullable = false, length = 20)
    private String name;
    @Column(nullable = false, length = 20)
    private String email;

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Long getPrimaryKey() {
        return primaryKey;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setPrimaryKey(Long primaryKey) {
        ValidUtils.checkIllegalArgumentOf(primaryKey);
        this.primaryKey = primaryKey;
    }

    public void setUserId(String userId) {
        ValidUtils.checkIllegalArgumentOf(userId);
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
