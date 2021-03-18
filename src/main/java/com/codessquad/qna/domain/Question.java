package com.codessquad.qna.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Question {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    private String title;
    private String contents;
    private LocalDateTime postTime = LocalDateTime.now();
    private LocalDateTime updatedTime;

    public Long getId() {
        return id;
    }

    public String getWriter() {
        return writer.getUserId();
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

    public String getFormattedPostTime() {
        if (updatedTime == null) {
            return postTime.format(DATE_TIME_FORMATTER);
        }
        return updatedTime.format(DATE_TIME_FORMATTER);
    }

    public boolean isPostWriter(User user) {
        return user.isUserMatching(writer);
    }

    public void update(Question newQuestion) {
        this.title = newQuestion.title;
        this.contents = newQuestion.title;
        this.updatedTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", postTime=" + postTime +
                '}';
    }
}
