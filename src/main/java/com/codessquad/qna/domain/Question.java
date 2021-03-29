package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
public class Question extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    private String title;

    @Lob
    private String contents;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    @OneToMany(mappedBy = "question")
    @Where(clause = "deleted=false")
    @OrderBy("answerId DESC")
    @JsonBackReference
    private List<Answer> answers;

    private Integer countOfAnswers = 0;

    public Question() {
    }

    public Long getQuestionId() {
        return questionId;
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

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
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

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public boolean isEmpty() {
        if ("".equals(this.title) || this.title == null) {
            return true;
        }
        if ("".equals(this.contents) || this.contents == null) {
            return true;
        }

        return false;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void deleted() {
        deleted = true;
        answers.forEach(answer -> deleted());
    }

    public void updateQuestionInfo(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void updateQuestionInfo(Question question) {
        this.title = question.getTitle();
        this.contents = question.getContents();
    }

    public boolean isEqualWriter(User sessionUser) {
        return writer.equals(sessionUser);
    }

    public void increaseCountOfAnswers() {
        countOfAnswers += 1;
    }

    public void decreaseCountOfAnswers() {
        countOfAnswers -= 1;
    }

    public Integer getCountOfAnswers() {
        return countOfAnswers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + questionId +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
