package com.codessquad.qna.domain.question;

import com.codessquad.qna.domain.answer.Answer;
import com.codessquad.qna.domain.user.User;
import com.codessquad.qna.utils.DateFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(nullable = false, length=500)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contents;

    private String date;

    @OneToMany(mappedBy="question")
    private final List<Answer> answers = new ArrayList<>();

    private boolean deleted;

    public Question() {
        this.date = LocalDateTime.now().format(DateFormat.DEFAULT);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
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

    public String getDate() {
        return date;
    }

    public long getNotDeletedAnswersCount() {
        long answersCount = answers.stream().filter(answer -> !answer.isDeleted()).count();
        return answersCount;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
        answer.setQuestion(this);
    }

    public void removeAnswer(Answer answer) {
        answers.remove(answer);
        answer.setQuestion(null);
    }

    public boolean isWrittenBy(User user) {
        return writer.equals(user);
    }

    public void update(Question questionWithUpdatedInfo) {
        this.title = questionWithUpdatedInfo.title;
        this.contents = questionWithUpdatedInfo.contents;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
