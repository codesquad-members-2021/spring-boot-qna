package com.codessquad.qna.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @Lob
    private String contents;

    private LocalDateTime createdDate;

    public Answer() {
    }

    public Answer(Question question, String contents, User loginUser) {
        this.question = question;
        this.writer = loginUser;
        this.contents = contents;
        createdDate = LocalDateTime.now();
    }

    public Long getAnswerId() {
        return answerId;
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

    public String getFormattedCreatedDate(){
        if (createdDate == null){
            return "";
        }
        return createdDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return answerId.equals(answer.answerId);
    }

    public boolean isEqualWriter(User sessionUser) {
        return writer.isEqualUserId(sessionUser.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerId);
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + answerId +
                ", writer=" + writer +
                ", question=" + question +
                ", contents='" + contents + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
