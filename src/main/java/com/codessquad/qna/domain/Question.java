package com.codessquad.qna.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Question {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_userId"))
    private User userId;

    private String title;

    private String contents;

    private LocalDateTime createDate;

    public Question() {
    }

    public Question(User userId, String title, String contents) {
        this.userId = userId;
        this.title = title;
        this.contents = contents;
        this.createDate = LocalDateTime.now();
    }

    public String getFormattedCreateDate() {
        if (createDate == null) {
            return "";
        }
        return createDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public Long getId() {
        return id;
    }

    public boolean isRightId(Long selectedId) {
        return this.id == selectedId;
    }

    public User getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}
