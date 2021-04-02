package com.codessquad.qna.service;

import com.codessquad.qna.domain.*;
import com.codessquad.qna.domain.repository.AnswerRepository;
import com.codessquad.qna.exception.AnswerNotFoundException;
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
        answer.changeDeleteStatus();
        answerRepository.delete(answer);
    }

    public Result deleteById(Long id, User user) {
        Answer answer = findAnswerById(id);
        if (!verifyWriter(answer, user)) {
            return Result.fail("You Can Only Delete Your Answers");
        }
        answer.changeDeleteStatus();
        answerRepository.save(answer);

        return Result.ok();
    }

    public boolean verifyWriter(Answer answer, User user) {
        if (!answer.isAnswerWriter(user)) {
            //throw new IllegalUserAccessException();
            return false;
        }
        return true;
    }
}
