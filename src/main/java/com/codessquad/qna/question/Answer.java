package com.codessquad.qna.question;

import com.codessquad.qna.exception.InsufficientAuthenticationException;
import com.codessquad.qna.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    private LocalDateTime createDateTime = LocalDateTime.now();

    private LocalDateTime updateDateTime;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    public Answer() {
    }

    public Answer(String comment, LocalDateTime createDateTime, Question question, User writer) {
        this.comment = comment;
        this.createDateTime = createDateTime;
        this.question = question;
        this.writer = writer;
    }

    public static List<Answer> getDummyData() {
        return Arrays.asList(
                new Answer("국내에서 Ruby on Rails와 Play가 활성화되기 힘든 이유는 뭘까",
                        LocalDateTime.of(2016, 01, 15, 18, 47),
                        new Question(1L),
                        new User(1L, "", "", "", "")),
                new Answer("국내에서 Ruby on Rails와 Play가 활성화되기 힘든 이유는 뭘까",
                        LocalDateTime.of(2016, 01, 15, 18, 47),
                        new Question(2L),
                        new User(2L, "", "", "", "")),
                new Answer("국내에서 Ruby on Rails와 Play가 활성화되기 힘든 이유는 뭘까",
                        LocalDateTime.of(2016, 01, 15, 18, 47),
                        new Question(3L),
                        new User(4L, "", "", "", "")),
                new Answer("국내에서 Ruby on Rails와 Play가 활성화되기 힘든 이유는 뭘까",
                        LocalDateTime.of(2016, 01, 15, 18, 47),
                        new Question(1L),
                        new User(1L, "", "", "", ""))
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(LocalDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public void verifyWriter(User target) {
        try {
            writer.checkId(target.getId());
        } catch (IllegalArgumentException e) {
            throw new InsufficientAuthenticationException("권한이 없는 사용자입니다.", e);
        }
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", createDateTime=" + createDateTime +
                ", updateDateTime=" + updateDateTime +
                ", question=" + question +
                ", writer=" + writer +
                '}';
    }
}
