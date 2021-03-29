package com.codessquad.qna.service;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NotAuthorizedException;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionService questionService;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, QuestionService questionService) {
        this.answerRepository = answerRepository;
        this.questionService = questionService;
    }

    public Answer addAnswer(long questionId, User writer, String contents) {
        Question question = questionService.getQuestion(questionId);
        return answerRepository.save(new Answer(question, writer, contents));
    }

    public void deleteAnswer(long answerId, User tryToDelete) {
        Answer answer = getAnswer(answerId);
        if (!answer.isWriter(tryToDelete)) {
            throw new NotAuthorizedException();
        }
        answer.delete();
        answerRepository.save(answer);
    }

    public Answer getAnswer(long id) {
        return answerRepository.findById(id).orElseThrow(() -> new NotFoundException(id + " 답변을 찾을 수 없습니다."));
    }
}
