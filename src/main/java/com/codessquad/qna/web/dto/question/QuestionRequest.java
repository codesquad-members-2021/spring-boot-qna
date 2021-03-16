package com.codessquad.qna.web.dto.question;

public class QuestionRequest {

    private String title;
    private String contents;

    public QuestionRequest(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}
