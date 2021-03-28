package com.codessquad.qna.exception;

public class ExceptionMessages {

    public static String NEED_LOGIN = "질문글 작성을 위해서는 로그인이 필요합니다";
    public static String UNAUTHORIZED_FAILED_QUESTION = "질문 (수정/삭제) 실패 : 권한이 부족하여 실패합니다 (작성자가 아니거나 로그인하지 않음)";
    public static String UNAUTHORIZED_FAILED_LOGIN = "로그인되어있지않음 : 권한이 부족하여 실패합니다 (작성자가 아니거나 로그인하지 않음)";
    public static String FREE2ASK_BUT_DELETE = "질문글 삭제 실패 : 다른사람의 답변이 달린 질문글은 삭제할 수 없습니다";
    public static String FAILED_LOGIN = "로그인에 실패하였습니다";
    public static String PROFILE_MODIFICATION_FAIL = "개인정보 수정에 실패하였습니다";
}
