package com.codessquad.qna.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    private String title;
    private String contents;
    private LocalDateTime time;
    private int point;
    private boolean deleted;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    protected Question() {}

    public Question(String title, String contents) {
        this.title = title;
        this.contents = contents;
        this.deleted = false;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setPoint(int point) {
        this.point = point;
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

    public LocalDateTime getTime() {
        return time;
    }

    public int getPoint() {
        return point;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public boolean isWriter(User user) {
        return writer.equals(user);
    }

    public void updateContents(Question updatedQuestion) {
        this.title = updatedQuestion.title;
        this.contents = updatedQuestion.contents;
        this.time = LocalDateTime.now();
    }

    public void delete(){
        this.deleted = true;
    }
}
