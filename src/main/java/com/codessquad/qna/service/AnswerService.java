package com.codessquad.qna.service;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.AnswerRepository;
import com.codessquad.qna.valid.UserValidator;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionService questionService;

    public AnswerService(AnswerRepository answerRepository, QuestionService questionService) {
        this.answerRepository = answerRepository;
        this.questionService = questionService;
    }

    public void write(User writer, String contents, Long questionId) {
        UserValidator.validate(writer);
        Question question = questionService.findById(questionId);
        Answer answer = new Answer(writer, question, contents);
        answerRepository.save(answer);
    }

    public Answer findById(Long answerId) {
        return answerRepository.findById(answerId).orElseThrow(NullPointerException::new);
    }

    public void delete(Long id) {
        answerRepository.deleteById(id);
    }
}
