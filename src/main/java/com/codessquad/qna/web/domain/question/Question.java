package com.codessquad.qna.web.domain.question;

import com.codessquad.qna.web.domain.AbstractEntity;
import com.codessquad.qna.web.domain.answer.Answer;
import com.codessquad.qna.web.domain.user.User;
import com.codessquad.qna.web.dto.question.QuestionRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@SQLDelete(sql = "UPDATE QUESTION SET is_active = 0 WHERE id = ?")
@Where(clause = "is_active=1")
public class Question extends AbstractEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(nullable = false)
    @NotBlank(message = "Title is mandatory")
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "Please write the contents")
    private String contents;

    private boolean isActive = true;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @OrderBy("id DESC")
    @JsonIgnore
    private List<Answer> answers = new ArrayList<>();

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    protected Question() {

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
                "id=" + getId() +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", createdAt=" + getCreatedAt() +
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
