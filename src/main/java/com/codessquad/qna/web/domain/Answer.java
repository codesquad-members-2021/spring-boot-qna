package com.codessquad.qna.web.domain;

import com.codessquad.qna.web.utility.QuestionUtility;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Answer {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"), nullable=false)
    private User writer;

    @Column
    private String contents;

    @Column
    private LocalDateTime writtenDateTime;

    protected Answer() {}

    public Answer(Question question, User writer, String contents) {
        this.question = question;
        this.writer = writer;
        this.contents = contents;
        this.writtenDateTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Question getQuestion() {
        return question;
    }

    public User getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public String getFormattedWrittenDateTime() {
        return writtenDateTime.format(QuestionUtility.DATE_PATTERN);
    }
}
