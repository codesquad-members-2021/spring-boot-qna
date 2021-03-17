package com.codessquad.qna.service;

import com.codessquad.qna.controller.HttpSessionUtils;
import com.codessquad.qna.controller.QuestionController;
import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
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
    private final AnswerRepository answerRepository;

    public QuestionService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    public void create(String title, String contents, HttpSession session) {
        User loginUser = HttpSessionUtils.getSessionUser(session);
        Question question = new Question(loginUser, title, contents);
        logger.info("question {}. ", question);
        questionRepository.save(question);
    }

    public void update(Long id, String title, String contents) {
        Question question = questionRepository.findById(id).orElse(null);
        question.updateQuestion(title, contents);
        logger.info("question {}. ", question);
        questionRepository.save(question);
    }

    public void delete(Question question, Long id) {
        for (Answer answer : findAnswer(id)) {
            answer.delete();
        }
        question.delete();
        questionRepository.save(question);
    }

    public Question findById(Long id) {
        return questionRepository.findById(id).orElse(null);
    }

    public List<Question> findAllQuestion() {return questionRepository.findActiveQuestion();}

    public List<Answer> findAnswer(Long id) {
        return answerRepository.findActiveAnswer(id);
    }

    public boolean canDelete(Long id, Question question, User loginUser) {
        int allAnswers = findAnswer(id).size();
        int numCount = 0;
        for (Answer answer : findAnswer(id)) {
            if (question.getWriter().getId().equals(answer.getWriter().getId())) {
                numCount += 1;
            }
        }
        return (allAnswers == 0 && question.matchUser(loginUser)) || (allAnswers == numCount && question.matchUser(loginUser));
    }

}
