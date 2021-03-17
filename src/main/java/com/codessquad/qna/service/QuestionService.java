package com.codessquad.qna.service;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.QuestionRepository;
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

    public void save(Question question) {
        question.setTimeCreated();
        questionRepository.save(question);
    }

    public List<Question> listAllQuestions() {
        return questionRepository.findAllByOrderByIdDesc();
    }

    public Question findById(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Question Not Found"));
    }
}
