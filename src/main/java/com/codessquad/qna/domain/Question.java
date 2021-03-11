package com.codessquad.qna.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Question {
    private static final DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(nullable = false, length = 20)
    private String title;

    @Column(nullable = false, length = 2000)
    private String contents;
    private LocalDateTime currentDateTime;

    public Question() {
        currentDateTime = LocalDateTime.now();
    }

    public Question(User writer, String title, String contents) {
        super();
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.currentDateTime = LocalDateTime.now();
    }

    public void updateQuestion(String title, String contents) {
        this.title = title;
        this.contents = contents;
        this.currentDateTime = LocalDateTime.now();
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setCurrentDateTime(LocalDateTime currentDateTime) {
        this.currentDateTime = currentDateTime;
    }

    public Long getId() {
        return id;
    }

    public String getWriter() {
        return writer.getUserId();
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getCurrentDateTime() {
        return currentDateTime.format(pattern);
    }

    public boolean matchUser(User loginUser) {
        String userId = loginUser.getUserId();
        if(this.writer.getUserId().equals(userId)) {
            return true;
        }
        return false;
    }

}
