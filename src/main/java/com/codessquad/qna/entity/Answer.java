package com.codessquad.qna.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ANSWER")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @Column(nullable = false, length = 3000)
    private String contents;

    @Column(nullable = false)
    private LocalDateTime writeDateTime;

    protected Answer() {
    }

    public Answer(Question question, User writer, String contents) {
        this.question = question;
        this.writer = writer;
        this.contents = contents;
        this.writeDateTime = LocalDateTime.now();
    }
}
 
