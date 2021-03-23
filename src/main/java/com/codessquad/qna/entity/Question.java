package com.codessquad.qna.entity;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Table(name = "QUESTION")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 3000)
    private String contents;

    @Column(nullable = false)
    private LocalDateTime writeDateTime;

    @OneToMany(mappedBy = "question")
    @Where(clause = "deleted = false")
    private List<Answer> answers;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean deleted;

    protected Question() {
    }

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.writeDateTime = LocalDateTime.now();
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

    public LocalDateTime getWriteDateTime() {
        return writeDateTime;
    }

    public String getFormattedWriteDateTime() {
        return writeDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public void delete() {
        answers.stream().forEach(answer -> answer.delete());
        this.deleted = true;
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public boolean isWriter(User user) {
        return writer.isSameId(user);
    }

    public boolean canDeleted() {
        return answers.stream().filter(answer -> !answer.isWriter(this.writer)).count() == 0;
    }
}
