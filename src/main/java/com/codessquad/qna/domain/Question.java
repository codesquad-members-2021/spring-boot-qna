package com.codessquad.qna.domain;

import com.codessquad.qna.exception.ForbiddenException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    private String title;
    private String contents;
    private LocalDateTime createdDateTime;
    private int countOfAnswers;
    private boolean deleted;

    @OneToMany(mappedBy = "question")
    @Where(clause = "deleted = false")
    @JsonIgnore
    private List<Answer> answers;

    protected Question() {
    }

    public Question(String title, String contents) {
        this.title = title;
        this.contents = contents;
        this.createdDateTime = LocalDateTime.now();
        this.countOfAnswers = 0;
        this.deleted = false;
    }

    public Long getId() {
        return id;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public int getCountOfAnswers() {
        return countOfAnswers;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public boolean isWriter(User user) {
        return writer.equals(user);
    }

    public void updateContents(Question updatingQuestion) {
        this.title = updatingQuestion.title;
        this.contents = updatingQuestion.contents;
    }

    public void delete(User writer) {
        if (!this.writer.equals(writer)) {
            throw new ForbiddenException("다른 사람의 질문을 삭제할 수 없습니다.");
        }
        checkAnswersWriter(writer);
        deleteAnswers();
        this.deleted = true;
    }

    private void checkAnswersWriter(User writer) {
        for (Answer answer : answers) {
            if (!answer.matchesWriter(writer)) {
                throw new ForbiddenException("다른 사람의 답변이 존재하여 질문을 삭제할 수 없습니다.");
            }
        }
    }

    private void deleteAnswers() {
        for (Answer answer : answers) {
            answer.delete();
        }
    }

    public void addAnswer() {
        this.countOfAnswers += 1;
    }

    public void deleteAnswer() {
        this.countOfAnswers -= 1;
    }

}
