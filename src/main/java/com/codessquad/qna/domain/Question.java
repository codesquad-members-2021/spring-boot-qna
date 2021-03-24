package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long questionId;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @JsonProperty
    private String title;

    @Lob
    @JsonProperty
    private String contents;

    private LocalDateTime createdDate;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    @OneToMany(mappedBy = "question")
    @Where(clause = "deleted=false")
    @OrderBy("answerId DESC")
    @JsonBackReference
    private List<Answer> answers;

    public Question() {
        this.createdDate = LocalDateTime.now();
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

    public String getFormattedCreatedDate() {
        if (createdDate == null) {
            return "";
        }
        return createdDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
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
