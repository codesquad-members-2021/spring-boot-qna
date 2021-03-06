package com.codessquad.qna.repository;

import com.codessquad.qna.domain.Question;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryQnaRepository implements QnaRepository {

    private final List<Question> questions = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void save(Question question) {
        questions.add(question);
    }

    @Override
    public List<Question> findAll() {
        return questions;
    }

    @Override
    public Question findQuestionById(int index) {
        return questions.get(index - 1);
    }
}
