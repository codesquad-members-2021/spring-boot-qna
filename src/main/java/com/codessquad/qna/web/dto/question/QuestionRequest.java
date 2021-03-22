package com.codessquad.qna.web.dto.question;

import com.codessquad.qna.web.domain.question.Question;
import com.codessquad.qna.web.domain.user.User;

public class QuestionRequest {

    private String title;
    private String contents;

    protected QuestionRequest() {

    }

    public QuestionRequest(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }


    public Question toEntity(User writer) {
        return new Question.Builder()
                .writer(writer)
                .title(title)
                .contents(contents)
                .build();
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}
