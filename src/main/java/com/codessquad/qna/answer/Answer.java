package com.codessquad.qna.answer;

import com.codessquad.qna.common.BaseEntity;
import com.codessquad.qna.question.Question;
import com.codessquad.qna.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Answer extends BaseEntity {
    private String comment;
    private boolean deleted;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    protected Answer() {
    }

    public Answer(Long id, String comment, boolean deleted, LocalDateTime createDateTime, LocalDateTime updateDateTime, Question question, User writer) {
        super(id, createDateTime, updateDateTime);

        this.comment = comment;
        this.deleted = deleted;
        this.question = question;
        this.writer = writer;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String comment;
        private boolean deleted;
        private LocalDateTime createDateTime;
        private LocalDateTime updateDateTime;
        private Question question;
        private User writer;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setComment(String comment) {
            this.comment = comment;
            return this;
        }

        public Builder setDeleted(boolean deleted) {
            this.deleted = deleted;
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

        public Builder setQuestion(Question question) {
            this.question = question;
            return this;
        }

        public Builder setWriter(User writer) {
            this.writer = writer;
            return this;
        }

        public Answer build() {
            return new Answer(id, comment, deleted, createDateTime, updateDateTime, question, writer);
        }
    }

    public String getComment() {
        return comment;
    }

    public void delete() {
        deleted = true;
    }

    public Question getQuestion() {
        return question;
    }

    public User getWriter() {
        return writer;
    }

    public void verifyWriter(User target) {
        writer.verifyWith(target);
    }

    public boolean isWriterSameAs(User target) {
        return writer.equals(target);
    }

    public void update(Answer newAnswer) {
        verifyWriter(newAnswer.getWriter());

        this.comment = newAnswer.comment;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + getId() +
                ", comment='" + comment + '\'' +
                ", createDateTime=" + getCreateDateTime() +
                ", updateDateTime=" + getUpdateDateTime() +
                ", question=" + question.getId() +
                ", writer=" + writer +
                '}';
    }
}
