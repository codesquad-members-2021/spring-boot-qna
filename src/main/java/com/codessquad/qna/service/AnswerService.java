package com.codessquad.qna.service;

import com.codessquad.qna.model.Answer;
import com.codessquad.qna.model.Question;
import com.codessquad.qna.model.User;
import com.codessquad.qna.repository.AnswerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionService questionService;

    public AnswerService(AnswerRepository answerRepository, QuestionService questionService) {
        this.answerRepository = answerRepository;
        this.questionService = questionService;
    }

    public void save(Long id, Answer answer, User sessionUser) {
        Question question = questionService.findById(id);
        if (sessionUser.nonNull() && question.nonNull()) {
            answer.save(sessionUser, question);
            this.answerRepository.save(answer);
        }
    }

    public boolean update(Long id, Answer answer, User sessionUser) {
        Answer targetAnswer = verifyAnswer(id, sessionUser);
        if (targetAnswer.nonNull()) {
            targetAnswer.update(answer);
            this.answerRepository.save(targetAnswer);
            return true;
        }
        return false;
    }

    public void delete(Long id, User sessionUser) {
        Answer targetAnswer = verifyAnswer(id, sessionUser);
        if (targetAnswer.nonNull()) {
            targetAnswer.delete();
            this.answerRepository.save(targetAnswer);
        }
    }

    public Answer verifyAnswer(Long id, User sessionUser) {
        Answer targetAnswer = findById(id);
        if (sessionUser.nonNull() && targetAnswer.nonNull() && targetAnswer.matchWriter(sessionUser)) {
            return targetAnswer;
        }
        return new Answer();
    }

    public Answer findById(Long id) {
        Optional<Answer> answer = this.answerRepository.findById(id);
        return answer.orElseGet(Answer::new);
    }

    public Long findQuestionId(Long answerId) {
        Answer answer = findById(answerId);
        if (answer.nonNull()) {
            return answer.getQuestionId();
        }
        return (long) -1;
    }

}
