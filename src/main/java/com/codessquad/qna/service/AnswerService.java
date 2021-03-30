package com.codessquad.qna.service;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.repository.AnswerRepository;
import com.codessquad.qna.repository.QuestionRepository;
import com.codessquad.qna.util.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.util.HttpSessionUtils.checkAccessibleSessionUser;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    public Answer getOneById(long answerId) {
        return answerRepository.findById(answerId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 답변입니다."));
    }

    public Answer create(Long id, String contents, HttpSession session) {
        User loginUser = HttpSessionUtils.getUserFromSession(session);

        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 질문입니다."));

        question.increaseCountOfAnswers();

        return answerRepository.save(new Answer(question, contents, loginUser));
    }

    public Answer update(Answer targetAnswer, Answer newAnswerInfo, User sessionUser) {
        checkAccessibleSessionUser(sessionUser, targetAnswer);

        targetAnswer.updateQuestionInfo(newAnswerInfo);
        return answerRepository.save(newAnswerInfo);
    }

    public Answer remove(User sessionUser, Answer answer) {
        checkAccessibleSessionUser(sessionUser, answer);

        answer.getQuestion().deleteAnswer(answer);

        return answerRepository.save(answer);
    }
}
