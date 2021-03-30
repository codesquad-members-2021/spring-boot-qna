package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
public class Question extends CommonEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 3000)
    private String contents;

    @OneToMany(mappedBy = "question")
    @Where(clause = "deleted = false")
    @JsonIgnore
    private List<Answer> answers;

    @JsonProperty
    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer countOfAnswer = 0;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean deleted;

    protected Question() {
    }

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
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

    public List<Answer> getAnswers() {
        return answers;
    }

    public Integer getCountOfAnswer() {
        return countOfAnswer;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public void delete() {
        answers.stream().forEach(answer -> answer.delete());
        this.deleted = true;
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public boolean isWriter(User user) {
        return writer.isSameId(user);
    }

    public boolean canDeleted() {
        return answers.stream().filter(answer -> !answer.isWriter(this.writer)).count() == 0;
    }

    public void increaseAnswerCount() {
        this.countOfAnswer++;
    }

    public void decreaseAnswerCount() {
        this.countOfAnswer--;
    }
}
