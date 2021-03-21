package com.codessquad.qna.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    private String title;

    @Lob
    private String contents;

    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "question")
    @OrderBy("id ASC")
    private List<Answer> answers;

    public Question() {
        this.createdDate = LocalDateTime.now();
    }

    public Long getQuestionId() {
        return questionId;
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

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
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

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public String getFormattedCreatedDate() {
        if (createdDate == null) {
            return "";
        }
        return createdDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    }

    public boolean isEmpty() {
        if (this.writer == null) {
            return true;
        }
        if ("".equals(this.title) || this.title == null) {
            return true;
        }
        if ("".equals(this.contents) || this.contents == null) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + questionId +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }

    public void updateInfo(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public boolean isEqualWriter(User sessionUser) {
        return writer.isEqualUserId(sessionUser.getUserId());
    }
}
