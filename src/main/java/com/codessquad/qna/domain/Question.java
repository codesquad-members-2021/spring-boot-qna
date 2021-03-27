package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question extends AbstractEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_author"))
    private User author;

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @Lob
    @NotBlank(message = "본문을 입력해주세요.")
    private String contents;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @JsonIgnore
    @OrderBy("id desc")
    private final List<Answer> answers = new ArrayList<>();

    protected Question() {
    }

    public Question(User author, String title, String contents) {
        this.author = author;
        this.title = title;
        this.contents = contents;
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void update(Question updatedQuestion) {
        this.title = updatedQuestion.title;
        this.contents = updatedQuestion.contents;
    }

    public boolean isNotSameAuthor(User loginUser) {
        return !this.author.equals(loginUser);
    }

    public int getAnswerSize() {
        return answers.size();
    }

    public User getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}

