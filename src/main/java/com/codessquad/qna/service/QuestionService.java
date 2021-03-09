package com.codessquad.qna.service;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getQuestions() {
        return questionRepository.getQuestions();
    }

    public void addQuestion(Question question) {
        question.setId(questionRepository.getQuestions().size() + 1);
        questionRepository.addQuestion(question);
    }

    public Question getQuestionById(long id) {
        return questionRepository.getQuestions().get((int) id - 1);
    }
}
