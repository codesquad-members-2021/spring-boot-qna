package com.codessquad.qna.question;

import com.codessquad.qna.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 5000)
    private String contents;

    private LocalDateTime createDateTime;

    private LocalDateTime updateDateTime;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    protected Question() {
    }

    public Question(Long id, String title, String contents, LocalDateTime createDateTime, LocalDateTime updateDateTime, User writer, List<Answer> answers) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.createDateTime = createDateTime == null ? LocalDateTime.now() : createDateTime;
        this.updateDateTime = updateDateTime;
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

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public void verifyWriter(User target) {
        writer.verifyWith(target);
    }

    public void update(Question newQuestion) {
        verifyWriter(newQuestion.getWriter());

        this.title = newQuestion.title;
        this.contents = newQuestion.contents;
        this.updateDateTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", createDateTime=" + createDateTime +
                ", updateDateTime=" + updateDateTime +
                ", writer=" + writer +
                ", answers=" + answers +
                '}';
    }
}
