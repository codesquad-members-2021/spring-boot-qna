package com.codessquad.qna.question.domain;

import com.codessquad.qna.answer.domain.Answer;
import com.codessquad.qna.common.BaseTimeEntity;
import com.codessquad.qna.user.domain.User;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
public class Question extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private List<Answer> answers;

    @Column
    private boolean deleted = false;

    protected Question() {}

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
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

    public List<Answer> getAnswers() {
        return answers;
    }

    public void update(Question question) {
        this.title = question.title;
        this.contents = question.contents;
    }

    public void delete() {
        this.deleted = true;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
