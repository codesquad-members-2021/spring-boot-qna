package com.codessquad.qna.question.domain;

import com.codessquad.qna.answer.domain.Answer;
import com.codessquad.qna.common.BaseEntity;
import com.codessquad.qna.user.domain.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question extends BaseEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(nullable = false)
    private String title;

    @Lob
    private String contents;

    @OneToMany(mappedBy = "question")
    @OrderBy("id ASC")
    @Where(clause = "deleted = false")
    @JsonBackReference
    private List<Answer> answers = new ArrayList<>();

    protected Question() {}

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

    public void update(Question question) {
        this.title = question.title;
        this.contents = question.contents;
    }

    public boolean isDeletable() {
        return answers.size() == 0;
    }
}
