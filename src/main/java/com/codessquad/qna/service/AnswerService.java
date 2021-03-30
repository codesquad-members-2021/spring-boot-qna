package com.codessquad.qna.service;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.AnswerRepository;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.AnswerNotFoundException;
import com.codessquad.qna.exception.IllegalUserAccessException;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionService questionService;

    public AnswerService(AnswerRepository answerRepository, QuestionService questionService) {
        this.answerRepository = answerRepository;
        this.questionService = questionService;
    }

    public Answer save(User writer, String contents, Long questionId) {
        Question question = questionService.findById(questionId);
        Answer answer = new Answer(writer, question, contents);
        return answerRepository.save(answer);
    }

    public Answer findAnswerById(Long id) {
        return answerRepository.findById(id).orElseThrow(AnswerNotFoundException::new);
    }

    public void delete(Answer answer) {
        answerRepository.delete(answer);
    }

    public void deleteById(Long id, User user) {
        Answer answer = findAnswerById(id);
        verifyWriter(answer, user);
        answer.delete();
        answerRepository.save(answer);
    }

    public void verifyWriter(Answer answer, User user) {
        if (!answer.isAnswerWriter(user)) {
            throw new IllegalUserAccessException();
        }
    }
}
