package com.codessquad.qna.utils;

import com.codessquad.qna.exception.type.UnauthorizedException;

import java.util.Objects;

/**
 * Created by 68936@naver.com on 2021-03-19 오전 12:21
 * Blog : https://velog.io/@san
 * Github : https://github.com/sanhee
 */
public class ValidUtils {
    public static void checkIllegalArgumentOf(String str) {
        if(str == null || str.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
    public static void checkIllegalArgumentOf(String str, String str2) {
        if ((str == null || str.isEmpty()) && (str2 == null || str2.isEmpty())){
            throw new IllegalArgumentException();
        }
    }
    public static void checkIllegalArgumentOf(Long l) {
        if( l == null ){
            throw new IllegalArgumentException();
        }
    }

    public static void checkIllegalArgumentOf(Long l, Long l2) {
        if( (l == null) || (l2 == null) ){
            throw new IllegalArgumentException();
        }
    }

    public static void authenticateOfId(String selectedUser, String loginUser) {
        ValidUtils.checkIllegalArgumentOf(selectedUser, loginUser);
        if (!Objects.equals(selectedUser, loginUser)) {
            throw new UnauthorizedException();
        }
    }
}
