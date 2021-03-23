package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Where(clause = "deleted = false")
public class Question {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    @JsonProperty
    private User writer;

    @JsonProperty
    private String title;

    @Lob
    @JsonProperty
    private String contents;

    private LocalDateTime postTime;
    private LocalDateTime updatedPostTime;
    private boolean deleted;

    @OneToMany(mappedBy = "question")
    @OrderBy("id DESC")
    @Where(clause = "deleted = false")
    @JsonManagedReference
    private List<Answer> answers = new ArrayList<>();

    protected Question() {
    }

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.postTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getWriterUserId() {
        return writer.getUserId();
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getFormattedPostTime() {
        if (updatedPostTime == null) {
            return postTime.format(DATE_TIME_FORMATTER);
        }
        return updatedPostTime.format(DATE_TIME_FORMATTER);
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public int getAnswerNum() {
        return answers.size();
    }

    public boolean isAnswerEmpty() {
        return answers.isEmpty();
    }

    public boolean isPostWriter(User user) {
        return user.isUserMatching(writer);
    }

    public boolean isAnswerWriterSame() {
        for (Answer answer : answers) {
            if (!answer.isAnswerWriter(writer)) {
                return false;
            }
        }
        return true;
    }

    public void update(Question updatedQuestion) {
        this.title = updatedQuestion.title;
        this.contents = updatedQuestion.contents;
        this.updatedPostTime = LocalDateTime.now();
    }

    public void delete() {
        deleted = true;
    }

    public void deleteAnswers() {
        answers.clear();
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", postTime=" + postTime +
                '}';
    }
}
