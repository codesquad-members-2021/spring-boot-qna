package com.codessquad.qna.domain;

import javax.persistence.*;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.codessquad.qna.utils.SessionUtil.getLoginUser;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 5000)
    private String contents;

    private LocalDateTime creationDate;

    private final String PATTERN_FORMAT = "yyyy.MM.dd HH:mm:ss";

    public Question() {
    }

    public Question(Question question, HttpSession session) {
        this.writer = getLoginUser(session);
        this.title = question.title;
        this.contents = question.contents;
        this.creationDate = question.creationDate;

    }

    public Question(String writer, String title, String contents) {
        this.title = title;
        this.contents = contents;
        this.creationDate = LocalDateTime.now();
    }

    public String getFormattedCreatedDate() {
        if (creationDate == null) {
            return "";
        }
        return creationDate.format(DateTimeFormatter.ofPattern(PATTERN_FORMAT));
    }

    public Long getId() {
        return id;
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

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", writer='" + writer.getName() + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }


    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
