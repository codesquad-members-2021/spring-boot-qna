package com.codessquad.qna.service;

import com.codessquad.qna.entity.Question;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void addQuestion(Question question) {
        questionRepository.save(question);
    }

    public List<Question> getQuestions() {
        return questionRepository.findAll();
    }

    public Optional<Question> getQuestion(long index) {
        return questionRepository.findById(index);
    }
}
