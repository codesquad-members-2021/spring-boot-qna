package com.codessquad.qna.web.questions;

import com.codessquad.qna.web.answers.Answer;
import com.codessquad.qna.web.users.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answers;

    @Column(nullable = false)
    private String title;

    private String contents;

    private LocalDateTime reportingDateTime;

    public Question(String title, String contents) {
        this.title = title;
        this.contents = contents;
        this.reportingDateTime = LocalDateTime.now();
    }

    public Question() {
        this.reportingDateTime = LocalDateTime.now();
    }

    public boolean isMatchingWriter(User anotherWriter) {
        return writer.getId() == anotherWriter.getId();
    }

    public void update(String newTitle, String newContents) {
        if (!title.equals(newTitle)) {
            title = newTitle;
        }
        if (!contents.equals(newContents)) {
            contents = newContents;
        }
    }

    public int getSizeOfAnswers() {
        return answers.size();
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

    public void setWriter(User writer) {
        this.writer = writer;
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

    public LocalDateTime getReportingDateTime() {
        return reportingDateTime;
    }

    public void setReportingDateTime(LocalDateTime reportingDateTime) {
        this.reportingDateTime = reportingDateTime;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", writer=" + writer +
                ", answers=" + answers +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", reportingDateTime=" + reportingDateTime +
                '}';
    }
}
