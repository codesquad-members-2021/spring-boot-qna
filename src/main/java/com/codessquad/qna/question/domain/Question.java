package com.codessquad.qna.question.domain;

import com.codessquad.qna.answer.domain.Answer;
import com.codessquad.qna.answer.domain.Answers;
import com.codessquad.qna.common.BaseEntity;
import com.codessquad.qna.user.domain.User;

import javax.persistence.*;
import java.util.List;

@Entity
public class Question extends BaseEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(nullable = false)
    private String title;

    @Lob
    private String contents;

    @Embedded
    private Answers answers = new Answers();

    private Integer countOfAnswer = 0;

    protected Question() {}

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public User getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public List<Answer> getAnswers() {
        return answers.getList();
    }

    public Integer getCountOfAnswer() {
        return countOfAnswer;
    }

    public void addCountOfAnswer() {
        countOfAnswer++;
    }

    public void deleteCountOfAnswer() {
        countOfAnswer--;
    }

    public void update(Question question) {
        this.title = question.title;
        this.contents = question.contents;
    }

    public boolean isDeletable() {
        return answers.isEmpty();
    }
}
