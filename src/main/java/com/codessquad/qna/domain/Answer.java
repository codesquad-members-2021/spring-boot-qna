package com.codessquad.qna.domain;

import javax.persistence.*;

@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
    private Question question;

    @Column(nullable = false)
    private String contents;

    private String time;

    protected Answer() {

    }

    public Answer(User writer, Question question, String contents, String time) {
        this.writer = writer;
        this.question = question;
        this.contents = contents;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public User getWriter() {
        return writer;
    }

    public Question getQuestion() {
        return question;
    }

    public String getContents() {
        return contents;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", writer=" + writer +
                ", question=" + question +
                ", contents='" + contents + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
