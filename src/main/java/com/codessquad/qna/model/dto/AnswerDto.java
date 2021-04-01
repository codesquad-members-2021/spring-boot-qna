package com.codessquad.qna.model.dto;

import com.codessquad.qna.exception.EntityNotCreateException;
import com.codessquad.qna.model.Answer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AnswerDto {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private Long id;

    private QuestionDto question;

    private UserDto writer;

    private String contents;

    private String dateTime;

    private boolean deleted;

    public AnswerDto() {}

    public AnswerDto(Answer answer) {
        this.id = answer.getId();
        this.question = new QuestionDto(answer.getQuestion());
        this.writer = new UserDto(answer.getWriter());
        this.contents = answer.getContents();
        this.dateTime = answer.getDateTime();
        this.deleted = answer.isDeleted();
    }

    public Answer toEntity() {
        if (question == null || writer == null || contents == null || dateTime == null) {
            throw new EntityNotCreateException();
        }
        return new Answer(id, question.toEntity(), writer.toEntity(), contents, dateTime, deleted);
    }

    public void save(UserDto writerDto, QuestionDto questionDto) {
        this.writer = writerDto;
        this.question = questionDto;
        this.dateTime = LocalDateTime.now().format(dateTimeFormatter);
    }

    public void update(AnswerDto answerDto) {
        this.contents = answerDto.contents;
    }

    public void delete() {
        this.deleted = true;
    }

    public boolean matchWriter(UserDto userDto) {
        return userDto.matchUserId(writer.getUserId());
    }

    public Long getId() {
        return id;
    }

    public QuestionDto getQuestion() {
        return question;
    }

    public UserDto getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDateTime() {
        return dateTime;
    }

    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public String toString() {
        return "AnswerDto{" +
                "id=" + id +
                ", writer=" + writer +
                ", contents='" + contents + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
