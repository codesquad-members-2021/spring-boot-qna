package com.codessquad.qna.web.domain.answer;

import com.codessquad.qna.web.domain.AbstractEntity;
import com.codessquad.qna.web.domain.question.Question;
import com.codessquad.qna.web.domain.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@SQLDelete(sql = "UPDATE ANSWER SET is_active = '0' WHERE id = ?")
@Where(clause = "is_active=1")
public class Answer extends AbstractEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_writer"))
    @JsonProperty
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    @JsonProperty
    private Question question;

    @Column(nullable = false)
    @JsonProperty
    private String contents;

    private Boolean isActive = true;

    protected Answer() {

    }

    public Answer(User writer, Question question, String contents) {
        this.writer = writer;
        this.question = question;
        this.contents = contents;
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


    public void setQuestion(Question question) {
        this.question = question;
        if (!question.getAnswers().contains(this)) {
            question.getAnswers().add(this);
        }
    }

    public void setWriter(User writer) {
        this.writer = writer;
        if (!writer.getAnswers().contains(this)) {
            writer.getAnswers().add(this);
        }
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
