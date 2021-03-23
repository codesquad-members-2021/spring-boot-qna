package com.codessquad.qna.service;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.repository.AnswerRepository;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public Answer findAnswer(Long id) {
        return answerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 답변이 존재하지 않습니다."));
    }

    public Answer save(Answer answer) {
        return answerRepository.save(answer);
    }

    public void delete(User user, Answer answer) {
        checkValid(user, answer);
        answer.setDeletedTrue();
        answerRepository.save(answer);
    }

    private void checkValid(User user, Answer answer) {
        if (!answer.isAnswerWriter(user)) {
            throw new IllegalStateException("자신의 댓글에만 접근 가능합니다.");
        }
    }
}
