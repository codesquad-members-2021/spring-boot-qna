package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Where(clause = "deleted = false")
public class Answer {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    @JsonProperty
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    @JsonProperty
    @JsonBackReference
    private Question question;

    @Lob
    @JsonProperty
    private String contents;

    private LocalDateTime postTime;
    private boolean deleted;

    public Answer() {
    }

    public Answer(User writer, Question question, String contents) {
        this.writer = writer;
        this.question = question;
        this.contents = contents;
        postTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getWriterUserId() {
        return writer.getUserId();
    }

    public Question getQuestion() {
        return question;
    }

    public String getContents() {
        return contents;
    }

    public String getFormattedPostTime() {
        return postTime.format(DATE_TIME_FORMATTER);
    }

    public boolean isAnswerWriter(User user) {
        return user.isUserMatching(writer);
    }

    public void delete() {
        deleted = true;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", writer=" + writer +
                ", contents='" + contents + '\'' +
                ", createdTime=" + postTime +
                '}';
    }
}
