package com.codessquad.qna.domain;

import javax.persistence.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;


    @Column(nullable = false, length = 20)
    private String title;
    @Column(nullable = false, length = 500)
    private String contents;
    @Column(nullable = false, length = 20)
    private final ZonedDateTime time = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

    public User getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
    public Long getId() {
        return id;
    }

    public String getTime() {
        return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
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
    public void setId(Long id) {
        this.id = id;
    }
}
