package com.codessquad.qna.answer.domain;

import org.hibernate.annotations.Where;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Answers {
    @OneToMany(mappedBy = "question")
    @OrderBy("id DESC")
    @Where(clause = "deleted = false")
    private List<Answer> answers = new ArrayList<>();

    public List<Answer> getList() {
        return answers;
    }

    public boolean isEmpty() {
        return answers.isEmpty();
    }
}
