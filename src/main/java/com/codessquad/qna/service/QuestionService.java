package com.codessquad.qna.service;

import com.codessquad.qna.domain.*;
import com.codessquad.qna.exception.*;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpSession;

import java.util.List;

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

    public void update(Question question, Question updatedQuestion) {
        question.update(updatedQuestion);
        questionRepository.save(question);
    }

    public void delete(Question question) {
        questionRepository.delete(question);
    }

    public Question findQuestion(Long id) {
        return questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);
    }

    public List<Question> findQuestions() {
        return questionRepository.findAll();
    }

    public Page<Question> findPage(int pageNumber) {
        int pageSize = 15;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return questionRepository.findAll(pageable);
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

