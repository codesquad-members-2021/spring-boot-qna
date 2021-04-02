package com.codessquad.qna.web.domain;

import com.codessquad.qna.web.utility.TimeConstant;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Question {

    @Id
    @GeneratedValue

    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"), nullable = false)
    private User writer;

    @Column(nullable = false, length = 20)
    @NotBlank(message = "제목은 필수 입력값입니다.")
    private String title;

    @Column(nullable = false, length = 500)
    @NotBlank(message = "내용은 필수 입력값입니다.")
    private String contents;

    @Column(nullable = false, length = 20)
    private LocalDateTime writtenDateTime = LocalDateTime.now();

    @OneToMany(mappedBy = "question")
    @OrderBy("id ASC")
    private List<Answer> answers;

    protected Question() {
    }

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
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

    public Long getId() {
        return id;
    }

    public String getFormattedWrittenDateTime() {
        return writtenDateTime.format(TimeConstant.DATE_PATTERN);
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public boolean isWriter(User inputUser) {
        return this.writer.equals(inputUser);
    }

    public int getAnswersSize() {
        return answers.size();
    }

    public List<Answer> getAnswers() {
        return answers;
    }

}
