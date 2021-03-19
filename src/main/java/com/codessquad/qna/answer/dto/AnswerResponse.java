package com.codessquad.qna.answer.dto;

import com.codessquad.qna.answer.domain.Answer;
import com.codessquad.qna.common.BaseResponse;
import com.codessquad.qna.user.dto.UserResponse;

public class AnswerResponse extends BaseResponse {
    private Long questionId;
    private UserResponse writer;
    private String contents;

    public AnswerResponse(Builder builder) {
        super(builder);
        this.questionId = builder.questionId;
        this.writer = builder.writer;
        this.contents = builder.contents;
    }

    private static class Builder extends BaseResponse.Builder<Builder> {
        private Long questionId;
        private UserResponse writer;
        private String contents;

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        protected AnswerResponse build() {
            return new AnswerResponse(this);
        }

        private Builder questionId(Long questionId) {
            this.questionId = questionId;
            return this;
        }

        private Builder writer(UserResponse writer) {
            this.writer = writer;
            return this;
        }

        private Builder contents(String contents) {
            this.contents = contents;
            return this;
        }
    }

    public static AnswerResponse from(Answer answer) {
        Builder builder = new Builder()
                .id(answer.getId())
                .createdDate(answer.getCreatedDateTime())
                .modifiedDate(answer.getModifiedDateTime())
                .questionId(answer.getQuestion().getId())
                .writer(UserResponse.from(answer.getWriter()))
                .contents(answer.getContents());
        return new AnswerResponse(builder);
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
}
