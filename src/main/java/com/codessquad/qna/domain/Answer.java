package com.codessquad.qna.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Answer {
    private static final DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_writer"))
    private User writer;

    @Column(nullable = false, length = 2000)
    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String contents;

    @Column(nullable = false)
    private boolean isDelete;

    private LocalDateTime createdDateTime;

    public Answer() {
        createdDateTime = LocalDateTime.now();
    }

    public Answer(Question question, User writer, String contents) {
        this.question = question;
        this.writer = writer;
        this.contents = contents;
        this.isDelete = false;
        this.createdDateTime = LocalDateTime.now();
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

    public String getCreatedDateTime() {
        return createdDateTime.format(pattern);
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void delete() {
        this.isDelete = true;
    }

    public boolean matchUser(User loginUser) {
        String loginUserId = loginUser.getUserId();
        String writerId = this.writer.getUserId();
        return writerId.equals(loginUserId);
    }

    public boolean isDeleted() {
        return isDelete;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", contents='" + contents + '\'' +
                ", deleted=" + isDelete +
                ", createdDateTime=" + createdDateTime +
                '}';
    }

}
