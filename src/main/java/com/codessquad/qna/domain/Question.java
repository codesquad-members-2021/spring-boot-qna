package com.codessquad.qna.domain;

import javax.persistence.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY ) // JPA 에노테이션: Q-A관계 맵핑, question은 Answer 클래스가 ManyToOne으로 맺은 칼럼의 이름
    private List<Answer> answers;

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

    public List<Answer> getAnswers() {
        return answers;
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

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
