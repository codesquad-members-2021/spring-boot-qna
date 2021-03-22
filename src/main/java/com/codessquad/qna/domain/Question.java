package com.codessquad.qna.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private int index;
    private LocalDate nowDate;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    protected Question() {
    }

    public Question(String title, String content, User writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getNowDate() {
        return nowDate;
    }

    public void setNowDate(LocalDate nowDate) {
        this.nowDate = nowDate;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void updqte(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public boolean isSameWriter(User loginUser) {

        return this.writer.equals(loginUser);
    }

    @Override
    public String toString() {
        return "Question{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

}
