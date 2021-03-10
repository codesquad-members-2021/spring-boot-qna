package com.codessquad.qna.questions;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    private final List<Question> questions = Collections.synchronizedList(new ArrayList<>());

    public void addQuestion(Question question) {
        synchronized (questions) {
            question.setIndex(questions.size() + 1);
            questions.add(question);
        }
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public Optional<Question> getQuestion(int index) {
        int actualIndex = index - 1;
        if (actualIndex < 0 || questions.size() <= actualIndex) {
            return Optional.empty();
        }
        return Optional.of(questions.get(actualIndex));
    }
}
