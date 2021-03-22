package com.codessquad.qna.domain;

import com.codessquad.qna.utils.ValidUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL) // JPA 에노테이션: Q-A관계 맵핑, question은 Answer 클래스가 ManyToOne으로 맺은 칼럼의 이름
    private List<Answer> answers;

    @Column(nullable = false, length = 20)
    private String title;

    @Column(nullable = false, length = 500)
    private String contents;

    @Column(nullable = false, length = 20)
    private final LocalDateTime time = LocalDateTime.now();

    @Column(nullable = false)
    private boolean questionDeleted = false;

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
        List<Answer> enableAnswers = new ArrayList<>();

        for(Answer answer : answers){
            if(!answer.isDeleted()){
                enableAnswers.add(answer);
            }
        }

        return enableAnswers;
    }

    public boolean isDeleted() {
        return questionDeleted;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public void setTitle(String title) {
        ValidUtils.checkIllegalArgumentOf(title);
        this.title = title;
    }

    public void setContents(String contents) {
        ValidUtils.checkIllegalArgumentOf(contents);
        this.contents = contents;
    }
    public void setId(Long id) {
        ValidUtils.checkIllegalArgumentOf(id);
        this.id = id;
    }

    public void setDeleted(boolean deleted) {
        this.questionDeleted = deleted;
    }

    public void setAnswers(List<Answer> answers) { // 순환참조 문제점?? 우디 PR 내용중에 있었는데 나중에 확인.
        answers = Optional.ofNullable(answers).orElseThrow(IllegalArgumentException::new);
        this.answers = answers;
    }
}
