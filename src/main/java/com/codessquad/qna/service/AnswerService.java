package com.codessquad.qna.service;

import com.codessquad.qna.controller.HttpSessionUtils;
import com.codessquad.qna.domain.answer.Answer;
import com.codessquad.qna.domain.answer.AnswerRepository;
import com.codessquad.qna.domain.question.Question;
import com.codessquad.qna.domain.question.QuestionRepository;
import com.codessquad.qna.domain.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    @Transactional
    public Long create(Long questionId, Answer answer, HttpSession session) {
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(questionId).orElseThrow(
                () -> new IllegalStateException("해당 질문을 찾을 수 없습니다. id = " + questionId));
        int answerCount = answerRepository.countAnswersByQuestionId(questionId);
        question.setAnswerCount(++answerCount);
        answer.setQuestion(question);
        answer.setWriter(sessionedUser);

        return answerRepository.save(answer).getId();
    }

    @Transactional
    public Answer findById(Long id) {
        return answerRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 질문이 없습니다. id = " + id));
    }

    @Transactional
    public List<Answer> findAllByQuestionId(Long questionId) {
        return answerRepository.findAllByQuestionId(questionId);
    }

    @Transactional
    public Long update(Long id, Answer answerWithUpdateInfo) {
        Answer answer = answerRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("해당 답변이 없습니다. id = " + id));
        answer.update(answerWithUpdateInfo);
        return id;
    }

    @Transactional
    public Long delete(Long questionId, Long id, HttpSession session) {
        Question question = questionRepository.findById(questionId).orElseThrow(
                () -> new IllegalStateException("해당 글이 없습니다. id = " + id));
        Answer answer = answerRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("해당 답변이 없습니다. id = " + id));
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!answer.isWrittenBy(sessionedUser)) {
            throw new IllegalStateException("자신이 작성한 답변만 삭제할 수 있습니다.");
        }
        answerRepository.delete(answer);

        int answerCount = answerRepository.countAnswersByQuestionId(questionId);
        question.setAnswerCount(answerCount);

        return id;
    }
}
