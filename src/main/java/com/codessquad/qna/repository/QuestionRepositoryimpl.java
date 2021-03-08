package com.codessquad.qna.repository;

import com.codessquad.qna.domain.Question;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class QuestionRepositoryimpl implements QuestionRepository {

    private List<Question> questions = new ArrayList<>();

    @Override
    public void save(Question question) {
        question.setIndex(questions.size() + 1);
        question.setNowDate(LocalDate.now());
        questions.add(question);
    }

    @Override
    public Question findQuestionByIndex(int index) {

        return questions.get(index - 1);
    }

    @Override
    public List<Question> findQuestionList() {

        return questions;
    }
}
