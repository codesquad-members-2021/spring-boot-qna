package com.codessquad.qna.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    private String title;
    private String contents;
    private LocalDate postTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWriter() {
        return writer.getUserId();
    }

    public void setWriter(User user) {
        this.writer = user;
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

    public LocalDate getPostTime() {
        return postTime;
    }

    public void setPostTime() {
        postTime = LocalDate.now();
    }

    public boolean isQuestionWriter(User user){
        return user.isUserMatching(writer);
    }

    public void update(Question newQuestion){
        this.title = newQuestion.title;
        this.contents = newQuestion.title;
        setPostTime();
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", postTime=" + postTime +
                '}';
    }
}
