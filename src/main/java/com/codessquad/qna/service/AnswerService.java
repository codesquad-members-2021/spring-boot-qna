package com.codessquad.qna.service;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.AnswerRepository;
import com.codessquad.qna.repository.QuestionRepository;
import com.codessquad.qna.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {
    Logger logger = LoggerFactory.getLogger(AnswerService.class);
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    public Optional<Answer> getOneById(long answerId) {
        return answerRepository.findByAnswerIdAndDeletedFalse(answerId);
    }

    public void create(Long id, String contents, HttpSession session) {
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findByQuestionIdAndDeletedFalse(id).orElse(null);

        answerRepository.save(new Answer(question, contents, loginUser));
    }

    public void remove(Answer answer) {
        answer.deleted();
        Answer testAnswer = answerRepository.save(answer);
        logger.info("testAnswer: " + testAnswer.toString());
    }

    public List<Answer> findAll() {
        return answerRepository.findAllByAndDeletedFalse();
    }
}
