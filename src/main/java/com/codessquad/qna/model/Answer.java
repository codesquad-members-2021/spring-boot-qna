package com.codessquad.qna.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Answer {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_user"), nullable = false)
    private User writer;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private boolean isDelete;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"), nullable = false)
    private Question question;

    public boolean matchWriter(User loginUser) {
        return this.writer.matchId(loginUser.getId());
    }

    public Long getQuestionId() {
        return this.question.getId();
    }

    public void save(User writer, Question question) {
        this.writer = writer;
        this.date = new Date();
        this.question = question;
        this.isDelete = false;
    }

    public void update(Answer answer) {
        this.contents = answer.getContents();
        this.date = new Date();
    }

    public void delete() {
        this.isDelete = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDate() {
        SimpleDateFormat simpleDate = new SimpleDateFormat( "yyyy-MM-dd HH:mm");
        return simpleDate.format(this.date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "writer: " + this.writer.getUserId() + ", " +
                "contents: " + this.contents + ", " +
                "date: " + this.date + ", " +
                "question: " + this.question.getId() + ", ";
    }

}
