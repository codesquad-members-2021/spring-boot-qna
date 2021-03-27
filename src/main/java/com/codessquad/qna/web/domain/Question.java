package com.codessquad.qna.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question {
    private static final DateTimeFormatter QUESTION_DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_questions_writer"))
    private User writer;

    @Column(length = 45)
    private String title;

    @Column(length = 50000)
    private String contents;
    private LocalDateTime createdDateTime = LocalDateTime.now();

    @OneToMany(mappedBy = "question")
    @JsonIgnore
    @OrderBy("id ASC")
    private List<Answer> answers = new ArrayList<>();

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setCreatedDate() {
        this.createdDateTime = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public User getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public List<Answer> getAnswers() {
        return this.answers;
    }

    public long getAnswersSize() {
        return this.answers.size();
    }

    public String getFormattedCreatedDate() {
        if (createdDateTime == null) {
            return "";
        }
        return createdDateTime.format(QUESTION_DATETIME_FORMAT);
    }

    public boolean isSameWriter(User writer) {
        return this.writer.equals(writer);
    }

    public void update(Question question) {
        this.title = question.title;
        this.contents = question.contents;
    }
}
