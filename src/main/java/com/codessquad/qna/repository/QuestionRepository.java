package com.codessquad.qna.repository;

import com.codessquad.qna.domain.Question;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class QuestionRepository {

    private List<Question> questions = new ArrayList<>();

    public void save(Question question) {
        questions.add(question);
    }

    public List<Question> findAll() {
        return Collections.unmodifiableList(questions);
    }
}
