package com.codessquad.qna.answer.dto;

import com.codessquad.qna.answer.domain.Answer;
import com.codessquad.qna.user.dto.UserResponse;

import java.time.LocalDateTime;

import static com.codessquad.qna.common.DateUtils.format;

public class AnswerResponse {
    private Long id;
    private Long questionId;
    private UserResponse writer;
    private String contents;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    protected AnswerResponse() {}

    public AnswerResponse(Long id, Long questionId, UserResponse writer, String contents, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.questionId = questionId;
        this.writer = writer;
        this.contents = contents;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static AnswerResponse from(Answer answer) {
        return new AnswerResponse(
                answer.getId(),
                answer.getQuestion().getId(),
                UserResponse.from(answer.getWriter()),
                answer.getContents(),
                answer.getCreatedDate(),
                answer.getModifiedDate());
    }

    public Long getId() {
        return id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public UserResponse getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public String getFormattedCreatedDate() {
        return format(createdDate);
    }

    public String getFormattedModifiedDate() {
        return format(modifiedDate);
    }
}
