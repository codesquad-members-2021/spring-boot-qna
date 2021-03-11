package com.codessquad.qna.model;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Question {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_to_user"), nullable = false)
    private User user;

    public boolean nonNull() {
        return this.id != null;
    }

    public boolean matchUser(User user) {
        if (this.user == null) {
            return false;
        }
        return this.user.matchId(user.getId());
    }

    public void update(Question question) {
        this.title = question.getTitle();
        this.contents = question.getContents();
        this.date = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
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

    public String getDate() {
        SimpleDateFormat simpleDate = new SimpleDateFormat( "yyyy-MM-dd HH:mm");
        return simpleDate.format(this.date);
    }

    public void setDate() {
        this.date = new Date();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "writer: " + this.writer + ", " +
                "title: " + this.title + ", " +
                "contents: " + this.contents;
    }

}
