package com.codessquad.qna.domain;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@SQLDelete(sql = "UPDATE QUESTION SET is_active = 0 WHERE id = ?")
@Where(clause = "is_active=1")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    @Column(nullable = false, length = 20)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column
    private Boolean isActive = true;

    private LocalDateTime time = LocalDateTime.now();

    protected Question() {
    }

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public LocalDateTime getTime() {
        return this.time;
    }

    public long getId() {
        return id;
    }

    public User getWriter() {
        return writer;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public boolean isMatchingWriter(User user){
        return writer.equals(user);
    }

    public void update(Question question) {
        this.writer = question.getWriter();
        this.contents = question.getContents();
        this.time = question.getTime();
        this.title = question.getTitle();
    }

    @Override
    public String toString() {
        return "Question{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
