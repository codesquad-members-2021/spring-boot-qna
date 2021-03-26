package com.codessquad.qna.service;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.AnswerRepository;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public void save(User writer, Question question, String contents) {
        Answer answer = new Answer(writer, question, contents);
        answerRepository.save(answer);
    }

    public Answer findAnswerById(Long id) {
        return answerRepository.findById(id).orElseThrow(UserNotFoundException::new); // TODO: AnswerNotFoundException 만들기
    }

    public void delete(Answer answer) {
        answerRepository.delete(answer);
    }
}
