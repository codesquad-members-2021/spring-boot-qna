package com.codessquad.qna.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question extends AbstractEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_questions_writer"))
    private User writer;

    @Column(length = 45)
    private String title;

    @Column(length = 50000)
    private String contents;

    @OneToMany(mappedBy = "question")
    @JsonIgnore
    @OrderBy("id DESC")
    private List<Answer> answers = new ArrayList<>();

    private int countOfAnswers = 0;

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
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
        return this.answers;
    }

    public int getCountOfAnswers() {
        return this.countOfAnswers;
    }

    public boolean isSameWriter(User writer) {
        return this.writer.equals(writer);
    }

    public void update(Question question) {
        this.title = question.title;
        this.contents = question.contents;
    }

    public void upCountOfAnswer() {
        this.countOfAnswers += 1;
    }

    public void downCountOfAnswer() {
        this.countOfAnswers -= 1;
    }
}
