package com.codessquad.qna.question;

import com.codessquad.qna.answer.Answer;
import com.codessquad.qna.common.BaseEntity;
import com.codessquad.qna.exception.InsufficientAuthenticationException;
import com.codessquad.qna.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Question extends BaseEntity {
    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 5000)
    private String contents;

    private boolean deleted;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    protected Question() {
    }

    public Question(Long id, String title, String contents, LocalDateTime createDateTime, LocalDateTime updateDateTime, User writer, List<Answer> answers) {
        super(id, createDateTime, updateDateTime);

        this.title = title;
        this.contents = contents;
        this.writer = writer;
        this.answers = answers;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String title;
        private String contents;
        private LocalDateTime createDateTime = LocalDateTime.now();
        private LocalDateTime updateDateTime;
        private User writer;
        private List<Answer> answers;

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

        public Builder setWriter(User writer) {
            this.writer = writer;
            return this;
        }

        public Builder setAnswers(List<Answer> answers) {
            this.answers = answers;
            return this;
        }

        public Question build() {
            return new Question(id, title, contents, createDateTime, updateDateTime, writer, answers);
        }
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public void delete() {
        deleted = true;
    }

    public User getWriter() {
        return writer;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void verifyWriter(User target) {
        writer.verifyWith(target);
    }

    public boolean isWriterDifferentFrom(Answer answer) {
        return !answer.isWriterSameAs(writer);
    }

    public void checkDeletable(User writer) {
        verifyWriter(writer);

        boolean differentWriterExists = answers.stream().anyMatch(this::isWriterDifferentFrom);
        if (differentWriterExists) {
            throw new InsufficientAuthenticationException("다른 작성자가 작성한 답변이 있으면 삭제할 수 없습니다.");
        }
    }

    public void update(Question newQuestion) {
        verifyWriter(newQuestion.getWriter());

        this.title = newQuestion.title;
        this.contents = newQuestion.contents;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + getId() +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", createDateTime=" + getCreateDateTime() +
                ", updateDateTime=" + getUpdateDateTime() +
                ", writer=" + writer +
                ", answers=" + answers +
                '}';
    }
}
