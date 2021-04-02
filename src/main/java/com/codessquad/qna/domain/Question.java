package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Question extends AbstractEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    @JsonManagedReference
    private User writer;

    private String title;

    @Column(nullable = false, length = 2000)
    private String contents;

    @OneToMany(mappedBy = "question")
    @OrderBy("id DESC")
    @JsonBackReference
    private List<Answer> answers;

    private boolean deleted = false;

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    protected Question() {}

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
        return answers.stream().filter(answer -> !answer.isDeleted()).collect(Collectors.toList());
    }

    public int getAnswerNumber() {
        if (this.answers.isEmpty()) {
            return 0;
        }
        return getAnswers().size();
    }

    public Question updateQuestion(Question modifiedQuestion) {
        this.title = modifiedQuestion.getTitle();
        this.contents = modifiedQuestion.getContents();
        return this;
    }

    public boolean isSameUser(User user) {
        return this.writer.equals(user);
    }

    public void delete() {
        this.deleted = true;
        for (Answer a : this.answers) {
            a.changeDeleteStatus();
        }
    }

    @Override
    public String toString() {
        return "Question{" +
                super.toString() +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
