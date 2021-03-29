package com.codessquad.qna.exception;

public class ExceptionMessages {

    public static final String NEED_LOGIN = "질문글 작성을 위해서는 로그인이 필요합니다";
    public static final String UNAUTHORIZED_FAILED_QUESTION = "질문 (수정/삭제) 실패 : 권한이 부족하여 실패합니다 (작성자가 아니거나 로그인하지 않음)";
    public static final String UNAUTHORIZED_FAILED_LOGIN = "로그인되어있지않음 : 권한이 부족하여 실패합니다 (작성자가 아니거나 로그인하지 않음)";
    public static final String FREE2ASK_BUT_DELETE = "질문글 삭제 실패 : 다른사람의 답변이 달린 질문글은 삭제할 수 없습니다";
    public static final String FAILED_LOGIN = "로그인에 실패하였습니다";
    public static final String PROFILE_MODIFICATION_FAIL = "개인정보 수정에 실패하였습니다";
    public static final String ANYONE_NOT_LOGGED_IN = "로그인 한 유저가 존재하지 않습니다";
    public static final String NOT_FOUNDED_USER = "User 데이터가 존재하지 않습니다";
    public static final String NOT_FOUNDED_QUESTION = "Question 데이터가 존재하지 않습니다";
    public static final String NOT_FOUNDED_ANSWER = "Answer 데이터가 존재하지 않습니다";
    public static final String REDUNDANT_USERID = "중복된 유저 아이디로 회원가입할 수 없습니다";
}
