package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
public class Question {
    private static final DateTimeFormatter FORMATTER_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(nullable = false, length = 20)
    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String title;

    @Column(nullable = false, length = 2000)
    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String contents;

    private LocalDateTime createdDateTime = LocalDateTime.now();

    @OneToMany(mappedBy = "question")
    @JsonManagedReference
    @OrderBy("id ASC")
    private List<Answer> answers;

    @Column(nullable = false)
    private boolean isDelete;

    public Question() {
    }

    public Question(User writer, String title, String contents) {
        super();
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.isDelete = false;
    }

    public void updateQuestion(String title, String contents) {
        this.title = title;
        this.contents = contents;
        this.createdDateTime = LocalDateTime.now();
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
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

    public String getCreatedDateTime() {
        return createdDateTime.format(FORMATTER_PATTERN);
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public boolean matchUser(User loginUser) {
        String userId = loginUser.getUserId();
        return this.writer.getUserId().equals(userId);
    }

    public void delete() {
        this.isDelete = true;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public boolean canDelete(Question question, User loginUser, List<Answer> activeAnswers) {
        if (!question.matchUser(loginUser)) {
            return false;
        }
        if (activeAnswers.size() == 0) {
            return true;
        }
        for (Answer answer : activeAnswers) {
            if (!answer.isMatch(loginUser)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", writer=" + writer +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", createdDateTime=" + createdDateTime +
                '}';
    }

}
