package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.Question;
import com.codessquad.qna.web.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void postQuestion(Question question) {
        questionRepository.save(question);
    }

    public List<Question> findQuestions() {
        return questionRepository.findAll();
    }

    public Question findQuestion(int index) {
        return questionRepository.findByIndex(index);
    }
}
