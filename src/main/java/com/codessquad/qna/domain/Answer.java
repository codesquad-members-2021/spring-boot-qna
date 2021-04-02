package com.codessquad.qna.domain;

import com.codessquad.qna.domain.validationGroup.Submit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

@Entity
public class Answer extends BaseEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
    @NotNull
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    @NotNull
    private User writer;

    @Column(nullable = false, length = 3000)
    @NotBlank(groups = {Submit.class, Default.class})
    private String contents;

    @Column(columnDefinition = "boolean default false")
    @NotNull
    private boolean deleted;

    protected Answer() {
    }

    public Answer(Question question, User writer, String contents) {
        this.question = question;
        this.writer = writer;
        this.contents = contents;

        if(this.question != null){
            this.question.increaseAnswerCount();
        }
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

    public boolean isDeleted() {
        return this.deleted;
    }

    public void delete() {
        this.deleted = true;

        if(this.question != null){
            this.question.decreaseAnswerCount();
        }
    }

    public boolean isWriter(User user) {
        return writer.isSameId(user);
    }
}
