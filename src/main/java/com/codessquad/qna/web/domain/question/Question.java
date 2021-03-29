package com.codessquad.qna.web.domain.question;

import com.codessquad.qna.web.domain.answer.Answer;
import com.codessquad.qna.web.domain.user.User;
import com.codessquad.qna.web.dto.question.QuestionRequest;
import com.codessquad.qna.web.utils.DateTimeUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@SQLDelete(sql = "UPDATE QUESTION SET is_active = '0' WHERE id = ?")
@Where(clause = "is_active=1")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    private Boolean isActive = true;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @OrderBy("id DESC")
    @JsonIgnore
    private List<Answer> answers = new ArrayList<>();

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdAt = LocalDateTime.now();
    }

    protected Question() {

    }

    public Long getId() {
        return id;
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

    public String getCreatedAt() {
        return DateTimeUtils.stringOf(createdAt);
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
        if (answer.getQuestion() != this) {
            answer.setQuestion(this);
        }
    }

    public void update(QuestionRequest request) {
        this.title = request.getTitle();
        this.contents = request.getContents();
    }

    public boolean isMatchingWriter(User user) {
        return writer.isMatchingWriter(user);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    public static Builder build() {
        return new Builder();
    }

    public static Builder build(User writer) {
        return new Builder(writer);
    }


    static public class Builder {
        private User writer;
        private String title = "";
        private String contents = "";

        private Builder() {
        }

        private Builder(User writer) {
            this.writer = writer;
        }

        private Builder(Question question) {
            this.writer = question.writer;
            this.title = question.title;
            this.contents = question.contents;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder contents(String contents) {
            this.contents = contents;
            return this;
        }

        public Question build() {
            return new Question(writer, title, contents);
        }

    }
}
