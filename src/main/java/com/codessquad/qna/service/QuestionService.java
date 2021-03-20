package com.codessquad.qna.service;

import com.codessquad.qna.controller.HttpSessionUtils;
import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.repository.AnswerRepository;
import com.codessquad.qna.repository.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class QuestionService {
    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);

    private final QuestionRepository questionRepository;
    private final AnswerService answerService;

    public QuestionService(AnswerService answerService, QuestionRepository questionRepository) {
        this.answerService = answerService;
        this.questionRepository = questionRepository;
    }

    public void create(String title, String contents, HttpSession session) {
        User loginUser = HttpSessionUtils.getSessionUser(session);
        Question question = new Question(loginUser, title, contents);
        logger.info("question {}. ", question);
        questionRepository.save(question);
    }

    public void update(Long id, String title, String contents) {
        Question question = questionRepository.findById(id).orElseThrow(NotFoundException::new);
        question.updateQuestion(title, contents);
        logger.info("question {}. ", question);
        questionRepository.save(question);
    }

    public void delete(Long questionId, User loginUser) {
        List<Answer> activeAnswers = answerService.findAnswers(questionId);
        Question question = findById(questionId);
        if (question.canDelete(question, loginUser, activeAnswers)) {
            for (Answer answer : activeAnswers) {
                answer.delete();
            }
            question.delete();
            questionRepository.save(question);
        }
    }

    public Question findById(Long id) {
        return questionRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<Question> findAllQuestion() {
        return this.questionRepository.findAllByIsDeleteFalse();
    }

}
