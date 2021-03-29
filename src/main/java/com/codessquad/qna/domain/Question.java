package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Question {
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    @Id
    @JsonProperty
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonProperty
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @JsonProperty
    private String title;

    @OneToMany(mappedBy = "question")
    @OrderBy("id ASC")
    private List<Answer> answers = new ArrayList<>();

    @Lob
    @JsonProperty
    private String contents;

    private boolean deleted;

    @JsonProperty
    private int answerCount = 0;

    public Question() {

    }

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }


    public void delete() {
        this.deleted = true;
    }

    public boolean isMatchingWriter(User loginUser) {
        return this.writer.equals(loginUser);
    }

    public void update(Question updateQuestion) {
        this.title = updateQuestion.title;
        this.contents = updateQuestion.contents;
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
        answerCount++;
    }

    public void deleteAnswer(Answer answer) {
        if (answers.contains(answer)) {
            answer.deleted();
            answerCount--;
        }
    }


    @Override
    public String toString() {
        return "Qna{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
