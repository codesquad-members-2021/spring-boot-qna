package com.codessquad.qna.web.domain.answer;

import com.codessquad.qna.web.domain.question.Question;
import com.codessquad.qna.web.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Answer {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;


    @Column(nullable = false)
    private String contents;

    private LocalDateTime createdAt;


    protected Answer() {

    }

    public Answer(User writer, Question question, String contents) {
        this.writer = writer;
        this.question = question;
        this.contents = contents;
        this.createdAt = LocalDateTime.now();
    }

    public static Answer toEntity(User writer, Question question, String contents) {
        return new Answer(writer, question, contents);
    }

    public Long getId() {
        return id;
    }

    public User getWriter() {
        return writer;
    }

    public Question getQuestion() {
        return question;
    }

    public String getContents() {
        return contents;
    }

    public String getCreatedAt() {
        return createdAt.format(DATE_TIME_FORMATTER);
    }

    public void setQuestion(Question question) {
        this.question = question;
        if (!question.getAnswers().contains(this)) {
            question.getAnswers().add(this);
        }
    }

    public void setWriter(User writer) {
        this.writer = writer;
        if (!writer.getAnswers().contains(this)) {
            writer.getAnswers().add(this);
        }
    }


    public boolean isMatchingWriter(User user) {
        return writer.isMatchingWriter(user);
    }

}
