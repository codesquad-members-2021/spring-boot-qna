package com.codessquad.qna.service;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.DisplayStatus;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NoSearchObjectException;
import com.codessquad.qna.repository.AnswerRepository;
import com.codessquad.qna.repository.QuestionRepository;
import com.codessquad.qna.valid.UserValidator;
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
    public Answer write(User writer, String contents, Long questionId) {
        UserValidator.validate(writer);
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new NoSearchObjectException("질문"));
        Answer answer = new Answer(writer, question, contents);
        return answerRepository.save(answer);
    }

    public Answer findById(Long answerId) {
        return answerRepository.findById(answerId).orElseThrow(() -> new NoSearchObjectException("답변"));
    }

    @Transactional
    public Long delete(Long id, User user) {
        Answer answer = findById(id);
        user.checkSameUser(answer.getWriter().getId());
        answer.deleteAnswer(DisplayStatus.CLOSE);
        return id;
    }
}
