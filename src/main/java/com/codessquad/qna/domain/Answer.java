package com.codessquad.qna.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User writer;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private String contents;
    private LocalDateTime createDate;

    public Answer() {
    }

    public Answer(User writer, Question question, String contents) {
        this.writer = writer;
        this.contents = contents;
        this.createDate = LocalDateTime.now();
        this.question = question;
    }
}
