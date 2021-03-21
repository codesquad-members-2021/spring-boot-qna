package com.codessquad.qna.domain.dto;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.DisplayStatus;

public class AnswerDto {
    private final Long id;
    private final String writerUserId;
    private final DisplayStatus status;
    private Long questionId;
    private final String contents;
    private final String createDateTime;

    private AnswerDto(Answer answer) {
        this.id = answer.getId();
        this.writerUserId = answer.getWriter().getUserId();
        this.status = answer.getStatus();
        this.contents = answer.getContents();
        this.createDateTime = answer.getFormatCreateDateTime();
        this.questionId = answer.getQuestion().getId();
    }

    public static AnswerDto createDto(Answer answer) {
        return new AnswerDto(answer);
    }

    public Long getId() {
        return id;
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

    public String getWriterUserId() {
        return writerUserId;
    }

    public Long getQuestionId() {
        return questionId;
    }
}
