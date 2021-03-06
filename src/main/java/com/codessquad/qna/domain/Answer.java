package com.codessquad.qna.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_author"))
    private User author;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer"))
    private Question question;

    @Lob
    private String contents;
    private LocalDateTime date;

    public Answer() {
    }

    public Answer(User author, Question question, String contents) {
        this.author = author;
        this.question = question;
        this.contents = contents;
        this.date = LocalDateTime.now();
    }

    public String getDate() {
        if (date == null) {
            return "";
        }
        return date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return Objects.equals(id, answer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

