package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Where(clause = "deleted = false")
public class Question extends AbstractEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    @JsonProperty
    private User writer;

    @JsonProperty
    private String title;

    @Lob
    @JsonProperty
    private String contents;

    @JsonProperty
    private Integer countOfAnswer = 0;

    private boolean deleted;

    @OneToMany(mappedBy = "question")
    @OrderBy("id DESC")
    @Where(clause = "deleted = false")
    @JsonManagedReference
    private final List<Answer> answers = new ArrayList<>();

    protected Question() {
    }

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
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

    public List<Answer> getAnswers() {
        return answers;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public int getAnswerNum() {
        return answers.size();
    }

    public Integer getCountOfAnswer() {
        return this.countOfAnswer;
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
    }

    public void delete() {
        this.deleted = true;
    }

    public void deleteAnswers() {
        this.answers.clear();
    }

    public void addAnswer() {
        this.countOfAnswer++;
    }

    public void deleteAnswer() {
        this.countOfAnswer--;
    }

    @Override
    public String toString() {
        return "Question{" +
                super.toString() +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
