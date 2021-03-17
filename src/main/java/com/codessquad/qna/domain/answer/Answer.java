package com.codessquad.qna.domain.answer;

import com.codessquad.qna.domain.BaseTimeEntity;
import com.codessquad.qna.domain.question.Question;
import com.codessquad.qna.domain.user.User;

import javax.persistence.*;

@Entity
public class Answer extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn (foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @ManyToOne
    @JoinColumn (foreignKey = @ForeignKey(name = "fk_answer_to_user"))
    private User writer;

    private String comments;

    public Answer() {
    }

    public Answer(User writer, Question question, String comments) {
        this.writer = writer;
        this.question = question;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public Question getQuestion() {
        return question;
    }

    public User getWriter() {
        return writer;
    }

    public String getComments() {
        return comments;
    }

    public boolean matchWriter(User loginedUser) {
        return writer.equals(loginedUser);
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", question=" + question +
                ", writer=" + writer +
                ", comments='" + comments + '\'' +
                '}';
    }
}
