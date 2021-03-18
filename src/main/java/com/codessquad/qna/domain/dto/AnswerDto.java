package com.codessquad.qna.domain.dto;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.DisplayStatus;

public class AnswerDto {
    private final Long writerId;
    private final DisplayStatus status;
    private Long questionId;
    private final String contents;
    private final String createDateTime;

    public AnswerDto(Answer answer) {
        this.writerId = answer.getWriter().getId();
        this.status = answer.getStatus();
        this.contents = answer.getContents();
        this.createDateTime = answer.getFormatCreateDateTime();
        this.questionId = answer.getQuestion().getId();
    }


    public DisplayStatus getStatus() {
        return status;
    }

    public String getContents() {
        return contents;
    }

    public String getFormatCreateDateTime() {
        return createDateTime;
    }

    public Long getWriterId() {
        return writerId;
    }

    public Long getQuestionId() {
        return questionId;
    }
}
