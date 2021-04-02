package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.Answer;
import com.codessquad.qna.web.domain.Question;
import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.domain.repository.AnswerRepository;
import com.codessquad.qna.web.exception.AnswerNotFoundException;
import com.codessquad.qna.web.utility.SessionUtility;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionService questionService;

    private AnswerService(AnswerRepository answerRepository, QuestionService questionService) {
        this.answerRepository = answerRepository;
        this.questionService = questionService;
    }

    public void save(Long questionId, User sessionedUser, String contents) {
        Question question = questionService.findById(questionId);
        Answer answer = new Answer(question, sessionedUser, contents);
        answerRepository.save(answer);
    }

    public List<Answer> findByQuestionId(Long questionId) {
        return answerRepository.findByQuestionId(questionId);
    }

    public Answer findById(Long id) {
        return answerRepository.findById(id).orElseThrow(AnswerNotFoundException::new);
    }

    public void delete(Long id, User sessionedUser) {
        Answer answer = findById(id);
        answer.verifyWriter(sessionedUser);
        answerRepository.delete(answer);
    }
}
