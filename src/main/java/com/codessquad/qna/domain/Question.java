package com.codessquad.qna.domain;

import javax.persistence.*;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(nullable = false, length = 20)
    private String title;

    @Column(nullable = false)
    private String contents;

    private String time;

    protected Question() {

    }

    public Question(User writer, String title, String contents, String time) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.time = time;
    }

    public String getTime() {
        return this.time;
    }

    public long getId() {
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

    public void update(Question question) {
        this.writer = question.getWriter();
        this.contents = question.getContents();
        this.time = question.getTime();
        this.title = question.getTitle();
    }

    @Override
    public String toString() {
        return "Question{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
