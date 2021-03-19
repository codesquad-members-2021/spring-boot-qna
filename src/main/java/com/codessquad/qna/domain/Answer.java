package com.codessquad.qna.domain;

import com.codessquad.qna.utils.ValidUtils;

import javax.persistence.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Created by 68936@naver.com on 2021-03-16 오전 1:30
 * Blog : https://velog.io/@san
 * Github : https://github.com/sanhee
 */
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @Column(nullable = false, length = 20)
    private String replyId;
    @Column(nullable = false, length = 20)
    private String replyAuthor;
    @Column(nullable = false, length = 500)
    private String replyContents;

    @Column(nullable = false, length = 20)
    private final ZonedDateTime replyTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
    @Column(nullable = false)
    private boolean answerDeleted = false;

    public Long getAnswerId() {
        return answerId;
    }

    public String getTime() {
        return replyTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public Question getQuestion() {
        return question;
    }

    public String getReplyId() {
        return replyId;
    }

    public String getReplyAuthor() {
        return replyAuthor;
    }

    public String getReplyContents() {
        return replyContents;
    }

    public boolean isAnswerDeleted() {
        return answerDeleted;
    }

    public void setAnswerId(Long id) {
        this.answerId = id;
    }

    public void setQuestion(Question question) {
        question = Optional.ofNullable(question).orElseThrow(IllegalArgumentException::new);
        this.question = question;
    }

    public void setReplyId(String replyId) {
        ValidUtils.checkIllegalArgumentOf(replyId);
        this.replyId = replyId;
    }

    public void setReplyAuthor(String replyAuthor) {
        ValidUtils.checkIllegalArgumentOf(replyAuthor);
        this.replyAuthor = replyAuthor;
    }

    public void setReplyContents(String replyContents) {
        ValidUtils.checkIllegalArgumentOf(replyContents);
        this.replyContents = replyContents;
    }

    public void setAnswerDeleted(boolean answerDeleted) {
        this.answerDeleted = answerDeleted;
    }
}
