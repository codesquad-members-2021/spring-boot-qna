package com.codessquad.qna.repository;

import com.codessquad.qna.domain.Question;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class QuestionRepository {

    private List<Question> questions = new ArrayList<>();

    public void save(Question question) {
        question.setId((long) (questions.size() + 1));
        question.setWriteTime(LocalDateTime.now());
        questions.add(question);
    }

    public List<Question> findAll() {
        return Collections.unmodifiableList(questions);
    }

    public Question findById(int id) {
        return questions.stream()
                .filter(question -> question.getId() == id)
                .findAny()
                .orElse(null);
    }
}
