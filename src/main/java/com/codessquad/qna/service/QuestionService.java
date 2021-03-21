package com.codessquad.qna.service;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.AnswerRepository;
import com.codessquad.qna.repository.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    private final Logger logger = LoggerFactory.getLogger(QuestionService.class);
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
    }

    public void addQuestion(Question newQuestion, User user) {
        newQuestion.setWriter(user);
        questionRepository.save(newQuestion);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Optional<Question> getOneById(Long id) {
        return questionRepository.findById(id);
    }

    public void updateInfo(Question presentQuestion, Question referenceQuestion) {
        presentQuestion.updateQuestionInfo(referenceQuestion);
        questionRepository.save(presentQuestion);
    }

    public void remove(long id) {
        questionRepository.deleteById(id);
    }
}
