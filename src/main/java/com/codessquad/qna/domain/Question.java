package com.codessquad.qna.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_author"))
    private User author;
    private String title;
    @Lob
    private String contents;
    private LocalDateTime date;

    @OneToMany(mappedBy = "question")
    @OrderBy("id asc")
    private List<Answer> answers;

    public Question() {
    }

    public Question(User author, String title, String contents) {
        this.author = author;
        this.title = title;
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

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public boolean isNotSameAuthor(User loginUser) {
        return !this.author.equals(loginUser);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(id, question.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

