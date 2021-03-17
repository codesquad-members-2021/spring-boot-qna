package com.codessquad.qna.web.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Question {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


//    @JoinColumn(foreignKey = @ForeignKey(name = "fk_questions_writer"))
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_questions_writer"))
    private User writer;
    //length 지정
    private String title;
    // length 지정 안해주면 기본값 255 // 4000... 2000.. 지정 필요
    @Lob
    private String contents;
    private LocalDateTime createdDateTime = LocalDateTime.now();

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setCreatedDate() {
        this.createdDateTime = LocalDateTime.now();
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

    public String getFormattedCreatedDate() {
        if (createdDateTime == null) {
            return "";
        }
        return createdDateTime.format(DATE_TIME_FORMATTER);
    }

    public boolean isMatchingWriter(User writer) {
        return this.writer.equals(writer);
    }

    public void update(Question question) {
        this.title = question.title;
        this.contents = question.contents;
    }
}
