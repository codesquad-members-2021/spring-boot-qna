package com.codessquad.qna.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Question extends AbstractEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_to_user"), nullable = false)
    private User writer;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    @JsonIgnore
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    @OrderBy("id DESC")
    private List<Answer> answers;

    public boolean matchWriter(User writer) {
        return this.writer.matchId(writer.getId());
    }

    public boolean matchWriterOfAnswerList() {
        Long writerId = this.writer.getId();
        long answerCount = getAnswers().stream()
                .filter(answer -> !answer.getWriter().matchId(writerId))
                .count();
        return answerCount == 0;
    }

    public void save(User writer) {
        this.writer = writer;
    }

    public void update(Question question) {
        this.title = question.title;
        this.contents = question.contents;
    }

    public void delete() {
        this.deleted = true;
        this.answers.forEach(Answer::delete);
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User user) {
        this.writer = user;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<Answer> getAnswers() {
        return answers.stream()
                .filter(answer -> !answer.isDeleted())
                .collect(Collectors.toList());
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return super.toString() + ", " +
                "writer: " + this.writer.getUserId() + ", " +
                "title: " + this.title + ", " +
                "contents: " + this.contents + ", ";
    }

}
