package com.codessquad.qna.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Answer {
    public static final String ANSWER_DATETIME_FORMAT = "yyyy.MM.dd HH:mm";

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

    protected Answer() {
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
        return date.format(DateTimeFormatter.ofPattern(ANSWER_DATETIME_FORMAT));
    }

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public Question getQuestion() {
        return question;
    }

    public String getContents() {
        return contents;
    }
}

