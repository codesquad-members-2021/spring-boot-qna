package com.codessquad.qna.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Answer {
    // 퀘스천은 앤서의 원투매니 : 퀘스천 하나에는 앤서가 여러개 달린다
    // 앤서는 퀘스쳔의 매니투원 : 앤서여러개가 퀘스천 하나에 달릴수 있다

    // 유저(라이터)는 퀘스천의 원 투 매니 : 유저 하나가 여러개의 퀘스천을 만드들 수 있다
    // 퀘스천은 유저(라이터)의 매니 투 원 : 여러개의 퀘스천은 하나의 유저가 작성할 수 있다.

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @Lob
    private String contents;

    private LocalDateTime createdDateTime;

    public Answer() {

    }

    public Answer(User writer, Question question, String contents) {
        this.writer = writer;
        this.contents = contents;
        this.question = question;
        this.createdDateTime = LocalDateTime.now();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, writer, contents, createdDateTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return Objects.equals(id, answer.id) && Objects.equals(writer, answer.writer) && Objects.equals(contents, answer.contents) && Objects.equals(createdDateTime, answer.createdDateTime);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
