package com.codessquad.qna.web.exceptions.auth;

public class UnauthorizedAccessException extends RuntimeException {
    public static final String PASSWORD_NOT_MATCHING = "패스워드가 일치하지 않습니다";
    public static final String CANNOT_MODIFY_ANOTHER_USER = "타인의 개인정보를 수정할 수 없습니다";
    public static final String CANNOT_MODIFY_OR_DELETE_ANOTHER_USERS_QUESTION = "자신이 작성한 질문만 수정 혹은 삭제할 수 있습니다";
    public static final String CANNOT_MODIFY_ANOTHER_USERS_ANSWER = "자신의 답변만 삭제할 수 있습니다";
    public static final String CAN_NOT_DELETE_BECAUSE_ANOTHER_USERS_ANSWER_IS_EXISTS = "다른사람의 답변이 존재하므로 질문을 삭제할 수 없습니다";

    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
