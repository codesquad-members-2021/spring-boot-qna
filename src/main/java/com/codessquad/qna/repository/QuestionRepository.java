package com.codessquad.qna.repository;

import com.codessquad.qna.domain.Question;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionRepository {
    private final List<Question> questionList = new ArrayList<>();

    public void save(Question question) {
        question.setQuestionId(questionList.size() + 1);

        questionList.add(question);
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
