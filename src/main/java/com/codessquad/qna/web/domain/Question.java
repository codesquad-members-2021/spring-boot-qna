package com.codessquad.qna.web.domain;

import com.codessquad.qna.web.utility.QuestionUtility;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;

@Entity
public class Question {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 20)
    private String writer;

    @Column(nullable = false, length = 20)
    private String title;

    @Column(nullable = false, length = 500)
    private String contents;

    @Column(nullable = false, length = 20)
    private LocalDateTime writtenDateTime;

    protected Question() {
    }

    public Question(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.writtenDateTime = LocalDateTime.now();
    }

    public String getWriter() {
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
        return writtenDateTime.format(QuestionUtility.DATE_PATTERN);
    }

}
