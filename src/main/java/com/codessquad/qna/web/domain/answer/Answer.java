package com.codessquad.qna.web.domain.answer;

import com.codessquad.qna.web.domain.question.Question;
import com.codessquad.qna.web.domain.user.User;
import com.codessquad.qna.web.utils.DateTimeUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @Column(nullable = false)
    private String contents;

    private boolean isActive = true;

    private LocalDateTime createdAt;


    protected Answer() {

    }

    public Answer(User writer, Question question, String contents) {
        this.writer = writer;
        this.question = question;
        this.contents = contents;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public User getWriter() {
        return writer;
    }

    public Question getQuestion() {
        return question;
    }

    public String getContents() {
        return contents;
    }

    public String getCreatedAt() {
        return DateTimeUtils.stringOf(createdAt);
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public boolean isMatchingWriter(User user) {
        return writer.isMatchingWriter(user);
    }

    public static Builder build() {
        return new Builder();
    }

    public static Builder build(User writer, Question question) {
        return new Builder(writer, question);
    }

    static public class Builder {
        private User writer;
        private Question question;
        private String contents = "";

        private Builder() {
        }

        private Builder(User writer, Question question) {
            this.writer = writer;
            this.question = question;
        }

        public Builder contents(String contents) {
            this.contents = contents;
            return this;
        }

        public Answer build() {
            return new Answer(writer, question, contents);
        }

    }
}
