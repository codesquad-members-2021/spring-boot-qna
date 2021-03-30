package com.codessquad.qna.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @Lob
    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String dateTime;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public void save(User writer, Question question) {
        this.writer = writer;
        this.question = question;
        this.dateTime = LocalDateTime.now().format(dateTimeFormatter);
    }

    public void update(Answer answer) {
        this.contents = answer.contents;
    }

    public void delete() {
        this.deleted = true;
    }

    public boolean matchWriter(User user) {
        return this.writer.matchUserId(user);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", question=" + question +
                ", writer=" + writer +
                ", contents='" + contents + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
