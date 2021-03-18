package com.codessquad.qna.question;

import com.codessquad.qna.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 5000)
    private String contents;

    private LocalDateTime createDateTime = LocalDateTime.now();

    private LocalDateTime updateDateTime;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    protected Question() {
    }

    public Question(Long id) {
        this(id, null, null, null, null, null, null);
    }

    public Question(String title, String contents, User writer) {
        this(title, contents, LocalDateTime.now(), writer);
    }

    public Question(String title, String contents, LocalDateTime createDateTime, User writer) {
        this(null, title, contents, createDateTime, null, writer, null);
    }

    public Question(Long id, String title, String contents, LocalDateTime createDateTime, LocalDateTime updateDateTime, User writer, List<Answer> answers) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.createDateTime = createDateTime;
        this.updateDateTime = updateDateTime;
        this.writer = writer;
        this.answers = answers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(LocalDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public void verifyWriter(User target) {
        writer.verifyWith(target);
    }

    public void update(Question newQuestion) {
        writer.verifyWith(newQuestion.getWriter());

        this.title = newQuestion.title;
        this.contents = newQuestion.contents;
        this.updateDateTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", createDateTime=" + createDateTime +
                ", updateDateTime=" + updateDateTime +
                ", writer=" + writer +
                ", answers=" + answers +
                '}';
    }
}
