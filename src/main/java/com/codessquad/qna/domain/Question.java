package com.codessquad.qna.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
public class Question {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    private String title;

    @Lob
    private String contents;

    private LocalDateTime postTime;
    private LocalDateTime updatedPostTime;

    @OneToMany(mappedBy = "question")
    @OrderBy("id asc")
    private List<Answer> answers;

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

    public int getAnswerNum() {
        return answers.size();
    }

    public boolean isPostWriter(User user) {
        return user.isUserMatching(writer);
    }

    public void update(Question updatedQuestion) {
        this.title = updatedQuestion.title;
        this.contents = updatedQuestion.contents;
        this.updatedPostTime = LocalDateTime.now();
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
