package com.codessquad.qna.domain.question;

import com.codessquad.qna.domain.BaseTimeEntity;
import com.codessquad.qna.domain.answer.Answer;
import com.codessquad.qna.domain.user.User;
import com.codessquad.qna.exception.AnotherAnswerException;
import com.codessquad.qna.exception.NotAuthorizationException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question extends BaseTimeEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contents;

    @OneToMany(mappedBy = "question")
    @OrderBy("id ASC")
    private final List<Answer> answers = new ArrayList<>();

    private int countOfAnswer;

    @JsonIgnore
    private boolean deleted;

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
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

    public Integer getCountOfAnswer() {
        return countOfAnswer;
    }

    public void delete() {
        deleted = true;
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
        answer.setQuestion(this);
        upCountOfAnswer();
    }

    private void upCountOfAnswer() {
        countOfAnswer++;
    }

    public void downCountOfAnswer() {
        countOfAnswer--;
    }

    public boolean isWrittenBy(User user) {
        if (!writer.equals(user)) {
            throw new NotAuthorizationException();
        }
        return true;
    }

    public boolean isAnsweredYourself(Answer answer) {
        if (!writer.equals(answer.getWriter())) {
            throw new AnotherAnswerException();
        }
        return true;
    }

    public void update(Question questionWithUpdatedInfo) {
        this.title = questionWithUpdatedInfo.title;
        this.contents = questionWithUpdatedInfo.contents;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + getId() +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", date='" + getCreateDateTime() + '\'' +
                '}';
    }
}
