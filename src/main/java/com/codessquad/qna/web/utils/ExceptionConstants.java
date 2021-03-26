package com.codessquad.qna.web.utils;

public class ExceptionConstants {

    public static final String PASSWORD_NOT_MATCHING = "패스워드가 일치하지 않습니다";
    public static final String CANNOT_MODIFY_ANOTHER_USER = "타인의 개인정보를 수정할 수 없습니다";
    public static final String CANNOT_MODIFY_OR_DELETE_ANOTHER_USERS_QUESTION = "자신이 작성한 질문만 수정 혹은 삭제할 수 있습니다";
    public static final String CANNOT_MODIFY_ANOTHER_USERS_ANSWER = "자신의 답변만 삭제할 수 있습니다";
    public static final String ONLY_FOR_LOGGED_IN_USER = "로그인된 사용자만 이용할 수 있는 기능입니다";
    public static final String EMPTY_FIELD_IN_USER_ENTITY = "비어있는 항목이 있어서 회원가입에 실패했습니다";
    public static final String EMPTY_FIELD_IN_QUESTION_ENTITY = "비어있는 항목이 있어서 질문 작성에 실패했습니다";
    public static final String EMPTY_FIELD_IN_ANSWER_ENTITY = "비어있는 항목이 있어서 답변 작성에 실패했습니다";
    public static final String CAN_NOT_DELETE_BECAUSE_ANOTHER_USERS_ANSWER_IS_EXISTS = "다른사람의 답변이 존재하므로 질문을 삭제할 수 없습니다";

    private ExceptionConstants() {
    }

}
