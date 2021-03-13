package com.codessquad.qna.service;

import com.codessquad.qna.controller.HttpSessionUtils;
import com.codessquad.qna.controller.QuestionController;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.QuestionRepository;
import com.codessquad.qna.valid.QuestionValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class QuestionService {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void create(String title, String contents, HttpSession session) {
        User loginUser = HttpSessionUtils.getSessionUser(session);
        Question question = new Question(loginUser, title, contents);
        logger.info(QuestionValidation.validQuestion(question));
        logger.info("question {}. ", question);
        questionRepository.save(question);
    }

    public void update(Long id, String title, String contents) {
        Question question = questionRepository.findById(id).orElse(null);
        question.updateQuestion(title, contents);
        logger.info(QuestionValidation.validQuestion(question));
        logger.info("question {}. ", question);
        questionRepository.save(question);
    }

    public void delete(Question question) {
        questionRepository.delete(question);
    }

    public Question findById(Long id) {
        return questionRepository.findById(id).orElse(null);
    }

}
