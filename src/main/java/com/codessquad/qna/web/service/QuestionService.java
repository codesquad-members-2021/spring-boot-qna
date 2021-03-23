package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.Question;
import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.exception.IllegalEntityIdException;
import com.codessquad.qna.web.repository.QuestionRepository;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void postQuestion(Question question, User user) {
        question.setWriter(user);
        questionRepository.save(question);
    }

    public Iterable<Question> findQuestions() {
        return questionRepository.findAll();
    }

    public Question findQuestion(long id) {
        return questionRepository
                .findById(id)
                .orElseThrow(() -> new IllegalEntityIdException("id(번호)에 해당하는 질문이 없습니다"));
    }

    public void updateQuestion(Question originQuestion, Question question) {
        originQuestion.update(question);
        questionRepository.save(originQuestion);
    }

    public void deleteQuestion(Question question) {
        questionRepository.delete(question);
    }
}
