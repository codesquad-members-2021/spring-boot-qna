package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.Answer;
import com.codessquad.qna.web.domain.Question;
import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.domain.repository.AnswerRepository;
import com.codessquad.qna.web.utility.SessionUtility;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class AnswerService {

    private AnswerRepository answerRepository;

    private AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public void save(Answer answer) {
        answerRepository.save(answer);
    }

    public List<Answer> findByQuestionId(Long questionId) {
        return answerRepository.findByQuestionId(questionId);
    }

}
