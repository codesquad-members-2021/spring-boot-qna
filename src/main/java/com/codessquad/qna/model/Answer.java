package com.codessquad.qna.model;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Answer {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "question_id"))
    private Question question;

    public boolean nonNull() {
        return this.id != null;
    }

    public boolean matchWriter(User loginUser) {
        if (this.writer == null) {
            return false;
        }
        return this.writer.equals(loginUser.getUserId());
    }

    public Long getQuestionId() {
        if (this.question.nonNull()) {
            return this.question.getId();
        }
        return (long) -1;
    }

    public void update(Answer answer) {
        this.contents = answer.getContents();
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

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

}
