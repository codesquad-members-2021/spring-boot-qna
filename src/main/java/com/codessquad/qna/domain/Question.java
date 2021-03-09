package com.codessquad.qna.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"id"})
public class Question {
    public static final String QUESTION_DATETIME_FORMAT = "yyyy.MM.dd HH:mm";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_author"))
    private User author;
    private String title;
    @Lob
    private String contents;
    private LocalDateTime date;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @OrderBy("id asc")
    private List<Answer> answers;

    @Builder
    public Question(User author, String title, String contents) {
        this.author = author;
        this.title = title;
        this.contents = contents;
        this.date = LocalDateTime.now();
    }

    public String getDate() {
        if (date == null) {
            return "";
        }
        return date.format(DateTimeFormatter.ofPattern(QUESTION_DATETIME_FORMAT));
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public boolean isNotSameAuthor(User loginUser) {
        return !this.author.equals(loginUser);
    }
}

