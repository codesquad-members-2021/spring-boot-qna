package com.codessquad.qna.exception.type;

/**
 * Created by 68936@naver.com on 2021-03-18 오전 3:33
 * Blog : https://velog.io/@san
 * Github : https://github.com/sanhee
 */
public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException() {
        super("권한이 없습니다.");
    }
}
