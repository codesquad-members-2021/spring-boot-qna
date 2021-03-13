package com.codessquad.qna.model;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
public class Question {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_to_user"), nullable = false)
    private User writer;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Date date;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answers;

    public boolean nonNull() {
        return this.id != null;
    }

    public boolean matchWriter(User writer) {
        if (this.writer == null) {
            return false;
        }
        return this.writer.matchId(writer.getId());
    }

    public void save(User writer) {
        this.date = new Date();
        this.writer = writer;
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

    public User getWriter() {
        return writer;
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

    public String getDate() {
        SimpleDateFormat simpleDate = new SimpleDateFormat( "yyyy-MM-dd HH:mm");
        return simpleDate.format(this.date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "writer: " + this.writer.getUserId() + ", " +
                "title: " + this.title + ", " +
                "contents: " + this.contents + ", " +
                "date: " + this.date;
    }

}
