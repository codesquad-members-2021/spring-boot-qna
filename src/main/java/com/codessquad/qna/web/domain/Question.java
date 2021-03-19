package com.codessquad.qna.web.domain;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@SQLDelete(sql = "UPDATE QUESTION SET DELETED = TRUE WHERE ID = ?")
public class Question extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    @Where(clause = "deleted=false")
    private List<Answer> answers;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean deleted = false;

    public Question(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public Question() {
    }

    public boolean isMatchingWriter(User anotherWriter) {
        return writer.isMatchingId(anotherWriter);
    }

    public void update(String newTitle, String newContents) {
        title = newTitle;
        contents = newContents;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", writer=" + writer.getId() +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
