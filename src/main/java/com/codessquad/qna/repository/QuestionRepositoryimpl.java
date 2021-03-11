package com.codessquad.qna.repository;

import com.codessquad.qna.domain.Question;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class QuestionRepositoryimpl implements QuestionRepository {

    private List<Question> questions = new ArrayList<>();

    @Override
    public void save(Question question) {
        question.setIndex(questions.size() + 1);
        question.setNowDate(LocalDate.now());
        questions.add(question);
    }

    @Override
    public Optional<Question> findQuestionByIndex(int index) {

        return Optional.ofNullable(questions.get(index - 1));
    }

    @Override
    public List<Question> findQuestionList() {

        return Optional.ofNullable(questions).get();
    }

}
