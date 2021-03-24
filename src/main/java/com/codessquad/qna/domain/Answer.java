package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Where(clause = "deleted = false")
public class Answer extends AbstractEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    @JsonProperty
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    @JsonProperty
    @JsonBackReference
    private Question question;

    @Lob
    @JsonProperty
    private String contents;

    private boolean deleted;

    public Answer() {
    }

    public Answer(User writer, Question question, String contents) {
        this.writer = writer;
        this.question = question;
        this.contents = contents;
    }

    public String getWriterUserId() {
        return writer.getUserId();
    }

    public Question getQuestion() {
        return question;
    }

    public String getContents() {
        return contents;
    }

    public boolean isAnswerWriter(User user) {
        return user.isUserMatching(writer);
    }

    public void delete() {
        deleted = true;
    }

    @Override
    public String toString() {
        return "Answer{" +
                super.toString() +
                ", writer=" + writer +
                ", contents='" + contents + '\'' +
                '}';
    }
}
