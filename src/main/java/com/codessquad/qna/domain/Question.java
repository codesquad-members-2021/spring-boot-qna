package com.codessquad.qna.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Question {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    private String title;

    @Column(nullable = false, length = 2000)
    private String contents;

    private LocalDateTime timeCreated = LocalDateTime.now();

    @OneToMany(mappedBy = "question")
    @OrderBy("id ASC")
    private List<Answer> answers;

    private boolean deleted = false;

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    protected Question() {}

    public User getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public String getFormattedTimeCreated() {
        return timeCreated.format(DATE_TIME_FORMATTER);
    }

    public List<Answer> getAnswers() {
        return answers.stream().filter(answer -> !answer.isDeleted()).collect(Collectors.toList());
    }

    public int getAnswerNumber() {
        if (this.answers.size() == 0) {
            return 0;
        }
        return this.answers.size();
    }

    public Question updateQuestion(Question modifiedQuestion) {
        this.title = modifiedQuestion.getTitle();
        this.contents = modifiedQuestion.getContents();
        return this;
    }

    public boolean isSameUser(User user) {
        return this.writer.equals(user);
    }

    public void delete() {
        this.deleted = true;
        for (Answer a : this.answers) {
            a.delete();
        }
    }

    @Override
    public String toString() {
        return "Question{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", id=" + id +
                ", timeCreated='" + timeCreated + '\'' +
                '}';
    }
}
