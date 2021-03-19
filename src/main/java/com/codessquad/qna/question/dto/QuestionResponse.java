package com.codessquad.qna.question.dto;

import com.codessquad.qna.answer.domain.Answer;
import com.codessquad.qna.answer.dto.AnswerResponse;
import com.codessquad.qna.question.domain.Question;
import com.codessquad.qna.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.codessquad.qna.common.DateUtils.format;

public class QuestionResponse {
    private Long id;
    private User writer;
    private String title;
    private String contents;
    private List<Answer> answers;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    protected QuestionResponse() {}

    public QuestionResponse(Long id, User writer, String title, String contents, List<Answer> answers, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.answers = answers;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static QuestionResponse from(Question question) {
        return new QuestionResponse(
                question.getId(),
                question.getWriter(),
                question.getTitle(),
                question.getContents(),
                question.getAnswers(),
                question.getCreatedDate(),
                question.getModifiedDate());
    }

    public Long getId() {
        return id;
    }

    public User getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public List<AnswerResponse> getAnswers() {
        return answers.stream()
                .map(AnswerResponse::from)
                .collect(Collectors.toList());
    }

    public String getCreatedDate() {
        return format(createdDate);
    }

    public String getModifiedDate() {
        return format(modifiedDate);
    }
}
