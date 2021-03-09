package com.codessquad.qna.repository;

import com.codessquad.qna.domain.Question;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionRepository {
    private final List<Question> questions = new ArrayList<>();

    public boolean save(Question question) {
        question.setQuestionId(questions.size() + 1);

        return questions.add(question);
    }

    public List<Question> getAll() {
        return questions;
    }

    public Question getOne(int targetId) {
        return questions.stream()
                .filter(question -> question.getQuestionId() == targetId)
                .findAny()
                .get();
    }
}
