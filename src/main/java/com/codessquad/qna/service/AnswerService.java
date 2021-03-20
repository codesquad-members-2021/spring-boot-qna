package com.codessquad.qna.service;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.repository.AnswerRepository;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    public Question findQuestion(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new NotFoundException("해당 게시글이 존재하지 않습니다."));
    }

    public Answer findAnswer(Long id) {
        return answerRepository.findById(id).orElseThrow(() -> new NotFoundException("해당 답변이 존재하지 않습니다."));
    }

    public void save(Answer answer) {
        answerRepository.save(answer);
    }

    public void delete(User user, Answer answer) {
        checkValid(user, answer);
        answerRepository.delete(answer);
    }

    private void checkValid(User user, Answer answer) {
        if (!answer.isAnswerWriter(user)) {
            throw new IllegalStateException("자신의 댓글에만 접근 가능합니다.");
        }
    }
}
