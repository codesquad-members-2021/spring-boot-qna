package com.codessquad.qna.domain;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

import static com.codessquad.qna.utils.DateUtil.DATE_AND_TIME_FORMAT;
import static com.codessquad.qna.utils.SessionUtil.getLoginUser;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    private String contents;

    @Column(nullable = false)
    private boolean deleted = false;

    private LocalDateTime createdDateTime;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    @OrderBy("id ASC")
    @Where(clause = "deleted=false")
    private List<Answer> answerList;

    public Question() {
    }

    public Question(Question question, HttpSession session) {
        this.writer = getLoginUser(session);
        this.title = question.title;
        this.contents = question.contents;
        this.createdDateTime = question.createdDateTime;
    }

    public Question(Question question, User user) {
        this.writer = user;
        this.title = question.title;
        this.contents = question.contents;
        this.createdDateTime = question.createdDateTime;
    }

    public Question(String writer, String title, String contents) {
        this.title = title;
        this.contents = contents;
        this.createdDateTime = LocalDateTime.now();
    }

    public String getFormattedCreatedDate() {
        if (createdDateTime == null) {
            return "";
        }
        return createdDateTime.format(DATE_AND_TIME_FORMAT);
    }

    public Long getId() {
        return id;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", writer='" + writer.getName() + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void deleteQuestion() {
        this.deleted = true;
        this.answerList.forEach(Answer::deleteAnswer);
    }

    public boolean isDeletable() {
        for (Answer answer : this.answerList) {
            if (!answer.isSameWriter(this.writer)) {
                return false;
            }
        }
        return true;
    }

}
