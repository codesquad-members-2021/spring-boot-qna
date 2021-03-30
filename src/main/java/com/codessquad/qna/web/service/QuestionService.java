package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.answer.Answer;
import com.codessquad.qna.web.domain.answer.AnswerRepository;
import com.codessquad.qna.web.domain.question.Question;
import com.codessquad.qna.web.domain.question.QuestionRepository;
import com.codessquad.qna.web.domain.user.User;
import com.codessquad.qna.web.dto.question.QuestionRequest;
import com.codessquad.qna.web.exception.CrudNotAllowedException;
import com.codessquad.qna.web.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public QuestionService(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @Transactional
    public Question create(User loginUser, QuestionRequest request) {
        Question question = request.toEntity(loginUser);
        return questionRepository.save(question);
    }

    @Transactional
    public Question update(long questionId, User loginUser, QuestionRequest request) {
        Question question = verifiedQuestion(questionId, loginUser);
        question.update(request);
        return questionRepository.save(question);
    }

    @Transactional
    public void delete(long questionId, User loginUser) {
        Question question = verifiedQuestion(questionId, loginUser);
        questionRepository.delete(question);
    }

    public Question verifiedQuestion(long questionId, User loginUser) {
        Question question = getQuestionById(questionId);
        User writer = question.getWriter();

        if (!loginUser.isMatchingWriter(writer)) {
            throw new CrudNotAllowedException("Cannot edit other user's posts");
        }
        return question;
    }

    public List<Answer> findAllAnswer(long questionId) {
        return answerRepository.findByQuestionId(questionId);
    }

    public Question getQuestionById(long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot found question number " + questionId));
    }
}
