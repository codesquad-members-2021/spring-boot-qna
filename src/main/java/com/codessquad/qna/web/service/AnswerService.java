package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.answer.Answer;
import com.codessquad.qna.web.domain.answer.AnswerRepository;
import com.codessquad.qna.web.domain.question.Question;
import com.codessquad.qna.web.domain.question.QuestionRepository;
import com.codessquad.qna.web.domain.user.User;
import com.codessquad.qna.web.exception.CrudNotAllowedException;
import com.codessquad.qna.web.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    @Transactional
    public Answer create(long questionId, String contents, User loginUser) {
        Question question = getQuestionById(questionId);

        Answer answer = Answer.build(loginUser, question)
                .contents(contents)
                .build();

        return answerRepository.save(answer);
    }

    @Transactional
    public boolean delete(long questionId, long answerId, User loginUser) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot found answer number " + answerId));

        if (!answer.isMatchingWriter(loginUser)) {
            throw new CrudNotAllowedException("Only writer can delete this answer post!");
        }
        answerRepository.delete(answer);
        return true;
    }

    private Question getQuestionById(long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot found question number " + questionId));
    }
}
