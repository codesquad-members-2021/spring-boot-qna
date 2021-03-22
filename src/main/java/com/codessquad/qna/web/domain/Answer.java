package com.codessquad.qna.web.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Answer {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @Column(length = 1000)
    private String contents;
    private LocalDateTime createdDateTime;

    protected Answer() {
    }

    public Answer(User writer, Question question, String contents) {
        this.writer = writer;
        this.question = question;
        this.contents = contents;
        this.createdDateTime = LocalDateTime.now();
    }

    public User getWriter() {
        return this.writer;
    }

    public String getContents() {
        return this.contents;
    }

    public String getFormattedCreatedDate() {
        if (createdDateTime == null) {
            return "";
        }
        return createdDateTime.format(DATE_TIME_FORMATTER);
    }


}
