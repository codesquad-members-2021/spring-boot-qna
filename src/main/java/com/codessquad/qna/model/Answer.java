package com.codessquad.qna.model;

import com.codessquad.qna.model.dto.UserDto;

import javax.persistence.*;

@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @Lob
    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String dateTime;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    public Answer() {}

    public Answer(Long id, Question question, User writer, String contents, String dateTime, boolean deleted) {
        this.id = id;
        this.question = question;
        this.writer = writer;
        this.contents = contents;
        this.dateTime = dateTime;
        this.deleted = deleted;
    }

    public boolean matchWriter(UserDto userDto) {
        return userDto.matchUserId(writer.getUserId());
    }

    public void delete() {
        this.deleted = true;
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

    public String getDateTime() {
        return dateTime;
    }

    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", writer=" + writer +
                ", contents='" + contents + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
