package com.codessquad.qna.question.dto;

import com.codessquad.qna.answer.dto.AnswerResponse;
import com.codessquad.qna.common.BaseResponse;
import com.codessquad.qna.question.domain.Question;
import com.codessquad.qna.user.dto.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionResponse extends BaseResponse {
    private UserResponse writer;
    private String title;
    private String contents;
    private List<AnswerResponse> answers;

    private QuestionResponse(Builder builder) {
        super(builder);
        this.writer = builder.writer;
        this.title = builder.title;
        this.contents = builder.contents;
        this.answers = builder.answers;
    }

    private static class Builder extends BaseResponse.Builder<Builder> {
        private UserResponse writer;
        private String title;
        private String contents;
        private List<AnswerResponse> answers;

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        protected QuestionResponse build() {
            return new QuestionResponse(this);
        }

        private Builder writer(UserResponse writer) {
            this.writer = writer;
            return this;
        }

        private Builder title(String title) {
            this.title = title;
            return this;
        }

        private Builder contents(String contents) {
            this.contents = contents;
            return this;
        }

        private Builder answers(List<AnswerResponse> answers) {
            this.answers = answers;
            return this;
        }
    }

    public static QuestionResponse from(Question question) {
        Builder builder = new Builder()
                .id(question.getId())
                .createdDateTime(question.getCreatedDateTime())
                .modifiedDateTime(question.getModifiedDateTime())
                .title(question.getTitle())
                .writer(UserResponse.from(question.getWriter()))
                .contents(question.getContents())
                .answers(question.getAnswers().stream().map(AnswerResponse::from).collect(Collectors.toList()));
        return new QuestionResponse(builder);
    }

    public UserResponse getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public List<AnswerResponse> getAnswers() {
        return answers;
    }
}
