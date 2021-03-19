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

@Service
public class AnswerService {
    private static final Logger logger = LoggerFactory.getLogger(AnswerService.class);
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    public void create(Long id, String contents, HttpSession session) {
        Question question = questionRepository.findById(id).orElseThrow(NotFoundException::new);
        User loginUser = HttpSessionUtils.getSessionUser(session);
        Answer answer = new Answer(question, loginUser, contents);
        logger.info("answer : {}. ", answer);
        answerRepository.save(answer);
    }

    public void delete(Answer answer) {
        answer.delete();
        answerRepository.save(answer);
    }

    public Answer findById(Long id) {
        return answerRepository.findById(id).orElseThrow(NotFoundException::new);
    }

}
