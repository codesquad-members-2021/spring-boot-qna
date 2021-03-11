package com.codessquad.qna.service;

import com.codessquad.qna.controller.HttpSessionUtils;
import com.codessquad.qna.domain.question.Question;
import com.codessquad.qna.domain.question.QuestionRepository;
import com.codessquad.qna.domain.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Transactional
    public Long create(Question question, HttpSession session) {
        User sessionedUser = (User) session.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
        question.setWriter(sessionedUser);
        return questionRepository.save(question).getId();
    }

    @Transactional
    public Long update(Long id, Question questionWithUpdatedInfo) {
        Question question = questionRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 질문이 없습니다. id = " + id));
        question.update(questionWithUpdatedInfo);

        return id;
    }

    @Transactional
    public Question findById(Long id) {
        return questionRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 질문이 없습니다. id = " + id));
    }

    @Transactional
    public Iterable<Question> findAll() {
        return questionRepository.findAll();
    }

    @Transactional
    public void delete(Long id, HttpSession session) {
        Question question = questionRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 질문이 없습니다. id = " + id));
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!question.isWrittenBy(sessionedUser)) {
            throw new IllegalStateException("자신이 작성한 글만 삭제할 수 있습니다.");
        }
        questionRepository.delete(question);
    }
}
