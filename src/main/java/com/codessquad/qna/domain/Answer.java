package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long answerId;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    @JsonProperty
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    @JsonProperty
    @JsonManagedReference
    private Question question;

    @Lob
    @JsonProperty
    private String contents;

    private LocalDateTime createdDate;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    public Answer() {
    }

    public Answer(Question question, String contents, User loginUser) {
        this.question = question;
        this.writer = loginUser;
        this.contents = contents;
        createdDate = LocalDateTime.now();
        deleted = false;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void deleted() {
        deleted = true;
    }


    public boolean isEqualWriter(User sessionUser) {
        return writer.equals(sessionUser);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return answerId.equals(answer.answerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerId);
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answerId=" + answerId +
                ", writer=" + writer +
                ", question=" + question +
                ", contents='" + contents + '\'' +
                ", createdDate=" + createdDate +
                ", deleted=" + deleted +
                '}';
    }
}
