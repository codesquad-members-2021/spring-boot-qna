package com.codessquad.qna.web.domain;

import com.codessquad.qna.web.exception.UnauthorizedUserException;
import com.codessquad.qna.web.utility.TimeConstant;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    @Column(nullable = false)
    @NotBlank(message = "내용은 필수 입력값입니다.")
    private String contents;

    @Column
    private LocalDateTime writtenDateTime = LocalDateTime.now();;

    protected Answer() {}

    public Answer(Question question, User writer, String contents) {
        this.question = question;
        this.writer = writer;
        this.contents = contents;
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
        return writtenDateTime.format(TimeConstant.DATE_PATTERN);
    }

    public void verifyWriter(User user) {
        if (!this.writer.equals(user)) {
            throw new UnauthorizedUserException(UnauthorizedUserException.UNAUTHORIZED_USER_TO_ANSWER);
        }
    }

}
