package com.codessquad.qna.question;

import com.codessquad.qna.answer.AnswerDTO;
import com.codessquad.qna.common.Constant;
import com.codessquad.qna.user.UserDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public class QuestionDTO {
    private Long id;
    private String title;
    private String contents;

    @JsonFormat(pattern = Constant.DEFAULT_DATE_TIME_FORMAT)
    private LocalDateTime createDateTime;

    private LocalDateTime updateDateTime;
    private UserDTO writer;
    private List<AnswerDTO> answers;
    private int answersCount;

    public QuestionDTO() {
    }

    public QuestionDTO(Long id, String title, String contents, LocalDateTime createDateTime, LocalDateTime updateDateTime, UserDTO writer, List<AnswerDTO> answers, int answersCount) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.createDateTime = createDateTime;
        this.updateDateTime = updateDateTime;
        this.writer = writer;
        this.answers = answers;
        this.answersCount = answersCount;
    }

    public static QuestionDTO from(Question question) {
        return builder()
                .setId(question.getId())
                .setTitle(question.getTitle())
                .setContents(question.getContents())
                .setCreateDateTime(question.getCreateDateTime())
                .setUpdateDateTime(question.getUpdateDateTime())
                .setWriter(UserDTO.from(question.getWriter()))
                .setAnswers(AnswerDTO.from(question.getAnswers()))
                .setAnswersCount(question.getAnswers() != null ? question.getAnswers().size() : 0)
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {
        private Long id;
        private String title;
        private String contents;
        private LocalDateTime createDateTime;
        private LocalDateTime updateDateTime;
        private UserDTO writer;
        private List<AnswerDTO> answers;
        private int answersCount;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContents(String contents) {
            this.contents = contents;
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

        public Builder setWriter(UserDTO writer) {
            this.writer = writer;
            return this;
        }

        public Builder setAnswers(List<AnswerDTO> answers) {
            this.answers = answers;
            return this;
        }

        public Builder setAnswersCount(int answersCount) {
            this.answersCount = answersCount;
            return this;
        }

        public QuestionDTO build() {
            return new QuestionDTO(id, title, contents, createDateTime, updateDateTime, writer, answers, answersCount);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
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

    public UserDTO getWriter() {
        return writer;
    }

    public void setWriter(UserDTO writer) {
        this.writer = writer;
    }

    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDTO> answers) {
        this.answers = answers;
    }

    public int getAnswersCount() {
        return answersCount;
    }

    public void setAnswersCount(int answersCount) {
        this.answersCount = answersCount;
    }
}
