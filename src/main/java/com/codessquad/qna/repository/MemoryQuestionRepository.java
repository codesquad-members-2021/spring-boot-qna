package com.codessquad.qna.repository;

import com.codessquad.qna.domain.Question;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryQuestionRepository implements QuestionRepository {

    private final List<Question> questions = Collections.synchronizedList(new ArrayList<>());

    @Override
    public Question save(Question question) {
        questions.add(question);
        return question;
    }

    @Override
    public List<Question> findAll() {
        return questions;
    }

    @Override
    public Question findQuestionById(Long id) {
        return questions.get((int) (id - 1));
    }
}
