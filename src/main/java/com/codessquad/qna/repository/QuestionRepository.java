package com.codessquad.qna.repository;

import com.codessquad.qna.domain.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionRepository {
    private final List<Question> questionList = new ArrayList<>();

    public void save(Question newQuestion) {
        newQuestion.setQuestionId(questionList.size() + 1);
        questionList.add(newQuestion);
    }

    public List<Question> getAll() {
        return questionList;
    }

    public Question getOne(int targetId) {
        return questionList.stream()
                .filter(question -> question.getQuestionId() == targetId)
                .findAny()
                .get();
    }
}
