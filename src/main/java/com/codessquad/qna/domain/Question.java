package com.codessquad.qna.domain;

import com.codessquad.qna.util.DateTimeUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User writer;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @OrderBy("id ASC ")
    private List<Answer> answerList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private DisplayStatus status = DisplayStatus.OPEN;

    private String title;
    private String contents;
    private LocalDateTime createDateTime;

    public void addAnswer(Answer answer) {
        answerList.add(answer);
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public String getFormatCreateDateTime() {
        return createDateTime.format(DateTimeUtils.dateTimeFormatter);
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void changeWriter(User writer) {
        this.writer = writer;
    }

    public User getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }

    public DisplayStatus getStatus() {
        return status;
    }

    public void questionUpdate(Question question) {
        this.title = question.getTitle();
        this.contents = question.getContents();
    }

    public boolean checkSameUserFromAnswer() {
        for (Answer answer : answerList) {
            if (!answer.getWriter().checkId(writer.getId())) {
                return false;
            }
        }
        return true;
    }

    public void changeStatus(DisplayStatus displayStatus) {
        this.status = displayStatus;
    }

}
