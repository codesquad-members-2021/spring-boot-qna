package com.codessquad.qna.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Question {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    private String title;

    @Column(nullable = false, length = 2000)
    private String contents;

    private LocalDateTime timeCreated = LocalDateTime.now();

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    protected Question() {}

    public User getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public String getFormattedTimeCreated() {
        return timeCreated.format(DATE_TIME_FORMATTER);
    }

    public Question updateQuestion(Question modifiedQuestion) {
        this.title = modifiedQuestion.getTitle();
        this.contents = modifiedQuestion.getContents();
        return this;
    }

    public boolean isSameUser(User user) {
        return this.writer.equals(user);
    }

    @Override
    public String toString() {
        return "Question{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", id=" + id +
                ", timeCreated='" + timeCreated + '\'' +
                '}';
    }
}
