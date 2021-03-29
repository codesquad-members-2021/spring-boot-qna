package com.codessquad.qna.web.domain;

import com.codessquad.qna.web.exceptions.InvalidEntityException;
import com.codessquad.qna.web.exceptions.auth.UnauthorizedAccessException;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

import static com.codessquad.qna.web.exceptions.InvalidEntityException.EMPTY_FIELD_IN_QUESTION_ENTITY;
import static com.codessquad.qna.web.exceptions.auth.UnauthorizedAccessException.CANNOT_MODIFY_OR_DELETE_ANOTHER_USERS_QUESTION;
import static com.codessquad.qna.web.exceptions.auth.UnauthorizedAccessException.CAN_NOT_DELETE_BECAUSE_ANOTHER_USERS_ANSWER_IS_EXISTS;
import static com.codessquad.qna.web.utils.EntityCheckUtils.isNotEmpty;

@Entity
public class Question extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    @Where(clause = "deleted=false")
    @JsonManagedReference
    private List<Answer> answers;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private boolean deleted = false;

    public Question(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    protected Question() {
    }

    public void update(Question newQuestion) {
        title = newQuestion.title;
        contents = newQuestion.contents;
    }

    public void delete() {
        verifyIsDeletable();
        deleted = true;
        deleteAllAnswers();
    }

    private void verifyIsDeletable() {
        answers.forEach((answer) -> {
            if (!writer.isMatchingId(answer.getWriter())) {
                throw new UnauthorizedAccessException(CAN_NOT_DELETE_BECAUSE_ANOTHER_USERS_ANSWER_IS_EXISTS);
            }
        });
    }

    private void deleteAllAnswers() {
        answers.forEach(Answer::delete);
    }

    public boolean isValid() {
        return isNotEmpty(title) && isNotEmpty(contents);
    }

    public void verifyQuestionEntityIsValid() {
        if (!isValid()) {
            throw new InvalidEntityException(EMPTY_FIELD_IN_QUESTION_ENTITY);
        }
    }

    public boolean isMatchingWriter(User anotherWriter) {
        return writer.isMatchingId(anotherWriter);
    }

    public void verifyIsQuestionOwner(User writer) {
        if (!isMatchingWriter(writer)) {
            throw new UnauthorizedAccessException(CANNOT_MODIFY_OR_DELETE_ANOTHER_USERS_QUESTION);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", writer=" + writer.getId() +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
