package com.codessquad.qna.answer;

import com.codessquad.qna.common.Constant;
import com.codessquad.qna.question.QuestionDTO;
import com.codessquad.qna.user.UserDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class AnswerDTO {
    private Long id;
    private String comment;

    @JsonFormat(pattern = Constant.DEFAULT_DATE_TIME_FORMAT)
    private LocalDateTime createDateTime;

    private LocalDateTime updateDateTime;
    private QuestionDTO question;
    private UserDTO writer;

    public AnswerDTO() {
    }

    public AnswerDTO(Long id, String comment, LocalDateTime createDateTime, LocalDateTime updateDateTime, QuestionDTO question, UserDTO writer) {
        this.id = id;
        this.comment = comment;
        this.createDateTime = createDateTime;
        this.updateDateTime = updateDateTime;
        this.question = question;
        this.writer = writer;
    }

    public static AnswerDTO from(Answer answer) {
        return builder()
                .setId(answer.getId())
                .setComment(answer.getComment())
                .setCreateDateTime(answer.getCreateDateTime())
                .setUpdateDateTime(answer.getUpdateDateTime())
                .setQuestion(QuestionDTO.from(answer.getQuestion()))
                .setWriter(UserDTO.from(answer.getWriter()))
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String comment;
        private LocalDateTime createDateTime;
        private LocalDateTime updateDateTime;
        private QuestionDTO question;
        private UserDTO writer;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setComment(String comment) {
            this.comment = comment;
            return this;
        }

        public Builder setCreateDateTime(LocalDateTime createDateTime) {
            this.createDateTime = createDateTime;
            return this;
        }

        public Builder setUpdateDateTime(LocalDateTime updateDateTime) {
            this.updateDateTime = updateDateTime;
            return this;
        }

        public Builder setQuestion(QuestionDTO question) {
            this.question = question;
            return this;
        }

        public Builder setWriter(UserDTO writer) {
            this.writer = writer;
            return this;
        }

        public AnswerDTO build() {
            return new AnswerDTO(id, comment, createDateTime, updateDateTime, question, writer);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(LocalDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public QuestionDTO getQuestion() {
        return question;
    }

    public void setQuestion(QuestionDTO question) {
        this.question = question;
    }

    public UserDTO getWriter() {
        return writer;
    }

    public void setWriter(UserDTO writer) {
        this.writer = writer;
    }
}
