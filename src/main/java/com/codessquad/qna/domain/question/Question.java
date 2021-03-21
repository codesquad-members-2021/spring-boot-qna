package com.codessquad.qna.domain.question;

import com.codessquad.qna.domain.BaseTimeEntity;
import com.codessquad.qna.domain.answer.Answer;
import com.codessquad.qna.domain.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(length = 20000)
    private String content;

    @OneToMany(mappedBy = "question")
    @OrderBy("id DESC")
    private final List<Answer> answers = new ArrayList<>();

    private String title;

    public Question() {
    }

    public Question(User writer, String title, String content) {
        super();
        this.writer = writer;
        this.title = title;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public boolean isSameWriter(User user) {
        return writer.equals(user);
    }

    public void update(Question updateQuestion) {
        this.title = updateQuestion.title;
        this.content = updateQuestion.content;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
