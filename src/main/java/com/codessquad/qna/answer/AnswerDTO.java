package com.codessquad.qna.answer;

import com.codessquad.qna.common.BaseDTO;
import com.codessquad.qna.question.Question;
import com.codessquad.qna.user.UserDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class AnswerDTO extends BaseDTO {
    private String comment;
    private Long questionId;
    private UserDTO writer;
    private int answersCount;

    public AnswerDTO() {
    }

    public AnswerDTO(Long id, String comment, LocalDateTime createDateTime, LocalDateTime updateDateTime, Long questionId, UserDTO writer, int answersCount) {
        super(id, createDateTime, updateDateTime);

        this.comment = comment;
        this.questionId = questionId;
        this.writer = writer;
        this.answersCount = answersCount;
    }

    public static AnswerDTO from(Answer answer) {
        return builder()
                .setId(answer.getId())
                .setComment(answer.getComment())
                .setCreateDateTime(answer.getCreateDateTime())
                .setUpdateDateTime(answer.getUpdateDateTime())
                .setWriter(UserDTO.from(answer.getWriter()))
                .setQuestionId(answer.getQuestion().getId())
                .build();
    }

    public static AnswerDTO of(Answer answer, int answersCount) {
        return builder()
                .setId(answer.getId())
                .setComment(answer.getComment())
                .setCreateDateTime(answer.getCreateDateTime())
                .setUpdateDateTime(answer.getUpdateDateTime())
                .setWriter(UserDTO.from(answer.getWriter()))
                .setQuestionId(answer.getQuestion().getId())
                .setAnswersCount(answersCount)
                .build();
    }

    public static List<AnswerDTO> from(List<Answer> answers) {
        return answers.stream()
                .map(AnswerDTO::from)
                .collect(Collectors.toList());
    }

    public static Builder builder() {
        return new Builder();
    }

    public Answer toEntity() {
        return Answer.builder()
                .setId(getId())
                .setComment(comment)
                .setCreateDateTime(getCreateDateTime())
                .setQuestion(Question.builder().setId(questionId).build())
                .setWriter(writer.toEntity())
                .build();
    }

    public static class Builder {
        private Long id;
        private String comment;
        private LocalDateTime createDateTime;
        private LocalDateTime updateDateTime;
        private Long questionId;
        private UserDTO writer;
        private int answersCount;

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

        public Builder setQuestionId(Long questionId) {
            this.questionId = questionId;
            return this;
        }

        public Builder setWriter(UserDTO writer) {
            this.writer = writer;
            return this;
        }

        public Builder setAnswersCount(int answersCount) {
            this.answersCount = answersCount;
            return this;
        }

        public AnswerDTO build() {
            return new AnswerDTO(id, comment, createDateTime, updateDateTime, questionId, writer, answersCount);
        }

    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public UserDTO getWriter() {
        return writer;
    }

    public void setWriter(UserDTO writer) {
        this.writer = writer;
    }

    public int getAnswersCount() {
        return answersCount;
    }

    public void setAnswersCount(int answersCount) {
        this.answersCount = answersCount;
    }
}
