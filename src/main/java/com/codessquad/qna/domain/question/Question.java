package com.codessquad.qna.domain.question;

import com.codessquad.qna.domain.BaseTimeEntity;
import com.codessquad.qna.domain.answer.Answer;
import com.codessquad.qna.domain.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
public class Question extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;
    private String title;
    private String contents;

    private Integer countOfAnswer = 0;

    @OneToMany(mappedBy = "question")
    @OrderBy("id DESC")
    private List<Answer> answers;

    public Question() {
    }

    public Question(User writer, String title, String contents) {
        super();
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public long getId() {
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

    public boolean isSameWriter(User user) {
        return writer.equals(user);
    }

    public void update(Question updateQuestion) {
        this.title = updateQuestion.title;
        this.contents = updateQuestion.contents;
    }

    public void addAnswer() {
        this.countOfAnswer += 1;
    }

    public void deleteAnswer() {
        this.countOfAnswer -= 1;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
