package com.codessquad.qna.questions;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface QuestionService {
    List<Question> getQuestions();

    void addQuestion(Question question);

    Optional<Question> getQuestion(int index);
}
