package com.codessquad.qna.web.domain;

import javax.persistence.*;

@Entity
public class Answer extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @Column(nullable = false, length = 400)
    private String contents;

    @Column(nullable = false)
    private boolean deleted = false;

    public Answer(String contents, Question question, User writer) {
        this.contents = contents;
        this.question = question;
        this.writer = writer;
    }

    protected Answer() {
    }

    public boolean isValid() {
        if (contents == null) {
            return false;
        }
        return !contents.isEmpty();
    }

    public boolean isSameWriter(User writer) {
        return writer.isMatchingId(writer);
    }

    public void delete() {
        deleted = true;
    }

    public boolean isMatchingWriter(User anotherWriter) {
        return writer.isMatchingId(anotherWriter);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
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

    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", writerId=" + writer.getId() +
                ", questionId=" + question.getId() +
                ", contents='" + contents + '\'' +
                '}';
    }
}
