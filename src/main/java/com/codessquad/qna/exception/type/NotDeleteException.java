package com.codessquad.qna.exception.type;

/**
 * Created by 68936@naver.com on 2021-03-20 오전 4:26
 * Blog : https://velog.io/@san
 * Github : https://github.com/sanhee
 */
public class NotDeleteException extends RuntimeException{
    public NotDeleteException() {
        super("댓글을 삭제해야, 게시글을 지울 수 있습니다.");
    }
}
