package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.Question;
import com.codessquad.qna.web.repository.QuestionRepository;
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

    public void postQuestion(Question question) {
        questionRepository.save(question);
    }

    public List<Question> findQuestions() {
        return questionRepository.findALL();
    }

    public Question findQuestion(int index) {
        Optional<Question> foundQuestion = questionRepository.findByIndex(index);
        if(!foundQuestion.isPresent()){
            throw new IllegalStateException("index에 해당하는 qeustion이 없습니다");
        }
        return foundQuestion.get();
    }
}
