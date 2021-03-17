package com.codessquad.qna.service;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.AnswerNotFoundException;
import com.codessquad.qna.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.controller.HttpSessionUtils.getSessionUser;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public Answer create(User user, Question question, String contents) {
        Answer answer = new Answer(user, question, contents);
        return answerRepository.save(answer);
    }

    public boolean delete(Answer answer, HttpSession session, Long id) {
        User loginUser = getSessionUser(session);
        if (!answer.isSameAuthor(loginUser)) {
            return false;
        }
        answerRepository.deleteById(id);
        return true;
    }

    public Answer findAnswer(Long id) {
        return answerRepository.findById(id).orElseThrow(AnswerNotFoundException::new);
    }
}

