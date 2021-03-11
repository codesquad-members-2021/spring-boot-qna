package com.codessquad.qna.web.questions;

import com.codessquad.qna.web.answers.Answer;
import com.codessquad.qna.web.users.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Question {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answers;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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

    public boolean isMatchingWriterId(long anotherWriterId) {
        return writer.getId() == anotherWriterId;
    }

    public int getSizeOfAnswers() {
        return answers.size();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public LocalDateTime getreportingDateTime() {
        return reportingDateTime;
    }

    public void setreportingDateTime(LocalDateTime reportingDateTime) {
        this.reportingDateTime = reportingDateTime;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
