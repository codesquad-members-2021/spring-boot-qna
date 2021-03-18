package com.codessquad.qna.exception.type;

/**
 * Created by 68936@naver.com on 2021-03-18 오전 3:33
 * Blog : https://velog.io/@san
 * Github : https://github.com/sanhee
 */
public class NotFoundException extends RuntimeException{
    public NotFoundException() {
        super("찾을 수 없습니다.");
    }
}
