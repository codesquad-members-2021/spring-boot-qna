package com.codessquad.qna.domain.answer;

import com.codessquad.qna.domain.BaseTimeEntity;
import com.codessquad.qna.domain.question.Question;
import com.codessquad.qna.domain.user.User;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.Objects;

@Entity
@SQLDelete(sql = "UPDATE ANSWER SET DELETED = TRUE WHERE ID = ?")
public class Answer extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_user"))
    private User writer;

    @Column(length = 20000)
    private String comment;

    private boolean deleted = false;

    public Answer() {
    }

    public Answer(User writer, Question question, String comment) {
        this.writer = writer;
        this.question = question;
        this.comment = comment;
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

    public String getComment() {
        return comment;
    }

    public boolean matchWriter(User loggedInUser) {
        return writer.equals(loggedInUser);
    }

    public void delete() {
        this.deleted = true;
    }

    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Answer)) return false;
        Answer answer = (Answer) o;
        return Objects.equals(id, answer.id)
                && Objects.equals(writer, answer.writer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, writer);
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", question=" + question +
                ", writer=" + writer +
                ", comment='" + comment + '\'' +
                '}';
    }
}
