package com.codessquad.qna.service;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.Result;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.repository.AnswerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public Answer create(User user, Question question, String contents) {
        return save(new Answer(user, question, contents));
    }

    public Optional<Answer> findAnswerForDelete(Long id, User user) {
        Optional<Answer> answer = answerRepository.findById(id);
        if (!checkValid(user, answer)) {
            return Optional.empty();
        }
        return answer;
    }

    private boolean checkValid(User user, Optional<Answer> optionalAnswer) {
        Answer answer = optionalAnswer.orElseThrow(() -> new NotFoundException("해당 답변이 존재하지 않습니다."));
        return answer.isAnswerWriter(user);
    }

    public void delete(Question question, Answer answer) {
        answer.delete();
        question.decreaseAnswerCount();
        save(answer);
    }

    private Answer save(Answer answer) {
        return answerRepository.save(answer);
    }
}
