package com.codessquad.qna.web.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @Column(length = 1000)
    private String contents;
    private LocalDateTime createdDateTime;

    protected Answer() {
    }

    public Answer(User writer, String contents) {
        this.writer = writer;
        this.contents = contents;
        this.createdDateTime = LocalDateTime.now();
    }

}
