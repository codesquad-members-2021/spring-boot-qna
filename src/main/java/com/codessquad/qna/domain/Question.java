package com.codessquad.qna.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime time;
    private int point;

    protected Question() {}

    public Question(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public Long getId() {
        return id;
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

    public LocalDateTime getTime() {
        return time;
    }

    public int getPoint() {
        return point;
    }
}
