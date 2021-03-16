package com.codessquad.qna.service;

import com.codessquad.qna.domain.*;
import com.codessquad.qna.exception.*;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.controller.HttpSessionUtils.*;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void update(Question question) {
        questionRepository.save(question);
    }

    public void update(Question question, String title, String contents) {
        question.update(title, contents);
        questionRepository.save(question);
    }

    public void delete(Question question) {
        questionRepository.delete(question);
    }

    public Question findQuestion(Long id) {
        return questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);
    }

    public Question findVerifiedQuestion(Long id, HttpSession session) {
        Question question = questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);
        checkPermission(session, question);
        return question;
    }

    private void checkPermission(HttpSession session, Question question) {
        if (!isLoginUser(session)) {
            throw new IllegalUserAccessException("로그인이 필요합니다.");
        }
        User loginUser = getSessionUser(session);
        if (question.isNotSameAuthor(loginUser)) {
            throw new IllegalUserAccessException();
        }
    }
}

