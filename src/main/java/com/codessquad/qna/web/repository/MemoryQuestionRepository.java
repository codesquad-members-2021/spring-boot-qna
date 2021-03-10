package com.codessquad.qna.web.repository;

import com.codessquad.qna.web.domain.Question;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class MemoryQuestionRepository implements QuestionRepository {
    private List<Question> questions = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void save(Question question) {
        int index = questions.size() + 1;
        question.setIndex(index);
        questions.add(question);
    }

    @Override
    public List<Question> findAll() {
        return new ArrayList<>(questions);
    }

    @Override
    public Question findByIndex(int index) {
        return questions.get(index - 1);
    }
}
