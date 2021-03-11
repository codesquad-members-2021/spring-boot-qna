package com.codessquad.qna.web.answers;

import com.codessquad.qna.web.questions.Question;
import com.codessquad.qna.web.users.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @Column(nullable = false, length = 20)
    private String contents;

    private LocalDateTime reportingDate;

    public boolean isMatchingWriter(User suggestedWriter) {
        return writer.isMatchingId(suggestedWriter.getId());
    }

    public Answer(String contents) {
        this.contents = contents;
        reportingDate = LocalDateTime.now();
    }

    public Answer() {
        reportingDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public LocalDateTime getReportingDate() {
        return reportingDate;
    }

    public void setReportingDate(LocalDateTime reportingDate) {
        this.reportingDate = reportingDate;
    }
}
