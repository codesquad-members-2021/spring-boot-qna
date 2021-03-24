package com.codessquad.qna.exception.type;

/**
 * Created by 68936@naver.com on 2021-03-18 오전 3:32
 * Blog : https://velog.io/@san
 * Github : https://github.com/sanhee
 */
public class IncorrectAccountException extends RuntimeException{
    public IncorrectAccountException(String msg) {
        super(msg);
    }
}
