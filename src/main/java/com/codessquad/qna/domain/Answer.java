package com.codessquad.qna.domain;

import com.codessquad.qna.domain.dto.AnswerDto;
import com.codessquad.qna.util.DateTimeUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @JsonIgnore
    @JoinColumn(name = "question_id")
    private Question question;

    @Enumerated(EnumType.STRING)
    private DisplayStatus status = DisplayStatus.OPEN;

    private String contents;
    private LocalDateTime createDateTime;

    public Answer() {
    }

    public Answer(User writer, Question question, String contents) {
        this.writer = writer;
        this.contents = contents;
        this.createDateTime = LocalDateTime.now();
        this.question = question;
        question.addAnswer(this);
    }

    public String getFormatCreateDateTime() {
        return createDateTime.format(DateTimeUtils.dateTimeFormatter);
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public User getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public Long getId() {
        return id;
    }

    public DisplayStatus getStatus() {
        return status;
    }

    public Question getQuestion() {
        return question;
    }

    public void changeStatus(DisplayStatus displayStatus) {
        this.status = displayStatus;
    }

    public AnswerDto returnDto() {
        return new AnswerDto(this);
    }
}
