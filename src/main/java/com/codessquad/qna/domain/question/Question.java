package com.codessquad.qna.domain.question;

import com.codessquad.qna.domain.BaseTimeEntity;
import com.codessquad.qna.domain.answer.Answer;
import com.codessquad.qna.domain.user.User;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@SQLDelete(sql = "UPDATE QUESTION SET DELETED = TRUE WHERE ID = ?")
public class Question extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    private String title;

    @Column(length = 20000)
    private String content;

    private boolean deleted = false;

    @OneToMany(mappedBy = "question", cascade = CascadeType.PERSIST)
    @Where(clause = "deleted = false")
    private final List<Answer> answers = new ArrayList<>();


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

    public void delete() {
        deleteAllAnswers();
        this.deleted = true;
    }

    private void deleteAllAnswers() {
        answers.stream().filter(answer -> !answer.isDeleted())
                .forEach(answer -> answer.delete());
    }

    public boolean isSameWriter(User user) {
        return writer.equals(user);
    }

    public void update(Question updateQuestion) {
        this.title = updateQuestion.title;
        this.content = updateQuestion.content;
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    public void deleteAnswer(Answer answer) {
        if(answers.contains(answer)) {
            answer.delete();
        }
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
