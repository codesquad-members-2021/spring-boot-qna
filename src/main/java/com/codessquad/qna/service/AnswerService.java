package com.codessquad.qna.service;

import com.codessquad.qna.domain.*;
import com.codessquad.qna.exception.ForbiddenException;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.exception.UnauthorizedAccessException;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public AnswerService(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public Answer create(Long questionId, Answer answer, User writer) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(NotFoundException::new);
        answer.setQuestion(question);
        answer.setWriter(writer);
        return answerRepository.save(answer);
    }

    public void delete(Long answerId, Long questionId, User loginUser) {
        Answer answer = answerWithAuthentication(answerId, questionId, loginUser);
        answer.delete();
        answerRepository.save(answer);
    }

    public Answer answerWithAuthentication(Long questionId, Long answerId, User loginUser) {
        Answer answer = answerRepository.findByIdAndQuestionIdAndDeletedFalse(answerId, questionId)
                .orElseThrow(NotFoundException::new);
        if (!answer.matchesWriter(loginUser)) {
            throw new ForbiddenException("다른 사람의 답변을 수정하거나 삭제할 수 없습니다.");
        }
        return answer;
    }

    public void update(Long questionId, Long answerId, User loginUser, Answer updatingAnswer) {
        Answer answer = answerWithAuthentication(questionId, answerId, loginUser);
        answer.updateAnswer(updatingAnswer);
        answerRepository.save(answer);
    }
}
