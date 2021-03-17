package com.codessquad.qna.answer.dto;

import com.codessquad.qna.answer.domain.Answer;
import com.codessquad.qna.question.domain.Question;
import com.codessquad.qna.user.domain.User;

import java.time.LocalDateTime;

import static com.codessquad.qna.common.DateUtils.format;

public class AnswerResponse {
    private Long id;
    private Question question;
    private User writer;
    private String contents;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    protected AnswerResponse() {}

    public AnswerResponse(Long id, Question question, User writer, String contents, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.question = question;
        this.writer = writer;
        this.contents = contents;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static AnswerResponse of(Answer answer) {
        return new AnswerResponse(
                answer.getId(),
                answer.getQuestion(),
                answer.getWriter(),
                answer.getContents(),
                answer.getCreatedDate(),
                answer.getModifiedDate());
    }

    public Long getId() {
        return id;
    }

    public Question getQuestion() {
        return question;
    }

    public User getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public String getCreatedDate() {
        return format(createdDate);
    }

    public String getModifiedDate() {
        return format(modifiedDate);
    }
}
