package com.codessquad.qna.answer.application;

import com.codessquad.qna.answer.domain.Answer;
import com.codessquad.qna.answer.domain.AnswerRepository;
import com.codessquad.qna.answer.exception.AnswerDeletedException;
import com.codessquad.qna.answer.exception.AnswerNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public void deleteAnswer(Long id) {
        Answer answer = getAnswerFromRepository(id);
        answer.delete();
        answerRepository.save(answer);
    }

    public Long getWriterId(Long id) {
        return getAnswerFromRepository(id)
                .getWriter()
                .getId();
    }

    public Answer getAnswerFromRepository(Long id) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new AnswerNotFoundException(id));
        if (answer.isDeleted()) {
            throw new AnswerDeletedException(id);
        }
        return answer;
    }
}
