package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
public class Question {
    public static final String QUESTION_DATETIME_FORMAT = "yyyy.MM.dd HH:mm";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_author"))
    private User author;

    private String title;

    @Column(length = 2000)
    private String contents;

    @JsonProperty
    private Integer countOfAnswer = 0;

    @JsonProperty
    private LocalDateTime date;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @JsonIgnore
    @OrderBy("id desc")
    private List<Answer> answers;

    protected Question() {
    }

    public Question(User author, String title, String contents) {
        this.author = author;
        this.title = title;
        this.contents = contents;
        this.date = LocalDateTime.now();
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public boolean isNotSameAuthor(User loginUser) {
        return !this.author.equals(loginUser);
    }

    public void addAnswer() {
        this.countOfAnswer++;
    }

    public void deleteAnswer() {
        this.countOfAnswer--;
    }
    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public String getFormattedDate() {
        if (date == null) {
            return "";
        }
        return date.format(DateTimeFormatter.ofPattern(QUESTION_DATETIME_FORMAT));
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}

