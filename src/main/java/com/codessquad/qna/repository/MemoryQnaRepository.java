package com.codessquad.qna.repository;

import com.codessquad.qna.domain.Question;
import java.util.ArrayList;
import java.util.List;

public class MemoryQnaRepository implements QnaRepository{

    List<Question> questions = new ArrayList<>();

    public void save(Question question) {
        questions.add(question);
    }

    @Override
    public List<Question> findAll() {
        return questions;
    }

    @Override
    public Question findQuestionById(int index) {
        return questions.get(index-1);
    }
}
