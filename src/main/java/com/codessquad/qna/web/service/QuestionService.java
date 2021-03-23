package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.Answer;
import com.codessquad.qna.web.domain.Question;
import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.domain.repository.QuestionRepository;
import com.codessquad.qna.web.exception.QuestionNotFoundException;
import com.codessquad.qna.web.exception.InvalidUserException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private QuestionRepository questionRepository;
    private AnswerService answerService;

    private QuestionService(QuestionRepository questionRepository, AnswerService answerService) {
        this.questionRepository = questionRepository;
        this.answerService = answerService;
    }

    public void save(Question question) {
        questionRepository.save(question);
    }

    public void delete(Question question) { questionRepository.delete(question); }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question findById(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException("찾으시는 질문이 존재하지 않습니다."));
        return question;
    }

    public List<Answer> findAnswersByQuestionId(Long questionId) {
        return answerService.findByQuestionId(questionId);
    }

    public void verifyQuestionWriter(Question question, User user) {
        if (!question.isWriter(user)) {
            throw new InvalidUserException("글 작성자가 아닙니다.");
        }
    }
}
