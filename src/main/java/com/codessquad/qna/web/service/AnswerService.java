package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.Answer;
import com.codessquad.qna.web.domain.Question;
import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.exception.IllegalEntityIdException;
import com.codessquad.qna.web.repository.AnswerRepository;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public void postAnswer(User writer, Question question, String contents) {
        Answer answer = new Answer(writer, question, contents);
        answerRepository.save(answer);
    }

    public void deleteAnswer(Answer answer) {
        answerRepository.delete(answer);
    }

    public Answer findAnswer(long id) {
        return answerRepository
                .findById(id)
                .orElseThrow(() -> new IllegalEntityIdException("id(번호)에 해당하는 답변이 없습니다"));
    }
}
