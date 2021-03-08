package com.codessquad.qna.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"id"})
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_author"))
    private User author;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer"))
    private Question question;

    @Lob
    private String contents;
    private LocalDateTime date;

    @Builder
    public Answer(User author, Question question, String contents) {
        this.author = author;
        this.question = question;
        this.contents = contents;
        this.date = LocalDateTime.now();
    }

    public String getDate() {
        if (date == null) {
            return "";
        }
        return date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    }

}


