package com.codessquad.qna.web.domain;

import com.codessquad.qna.web.utility.TimeConstant;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Question {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"), nullable = false)
    private User writer;

    @Column(nullable = false, length = 20)
    private String title;

    @Column(nullable = false, length = 500)
    private String contents;

    @Column(nullable = false, length = 20)
    private LocalDateTime writtenDateTime = LocalDateTime.now();

    protected Question() {
    }

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
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

    public Long getId() {
        return id;
    }

    public String getFormattedWrittenDateTime() {
        return writtenDateTime.format(TimeConstant.DATE_PATTERN);
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public boolean isWriter(User inputUser) {
        return this.writer.equals(inputUser);
    }
}
