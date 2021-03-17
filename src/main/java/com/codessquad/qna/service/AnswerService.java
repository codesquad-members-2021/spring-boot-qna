package com.codessquad.qna.service;

import com.codessquad.qna.domain.*;
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

    public void create(Long questionId, Answer answer, User writer) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(NotFoundException::new);
        answer.setQuestion(question);
        answer.setWriter(writer);
        answerRepository.save(answer);
    }

    public void delete(Long answerId, Long questionId, User loginUser) {
        Answer answer = getWithAuthentication(answerId, questionId, loginUser);
        answer.delete();
        answerRepository.save(answer);
    }

    public Answer getWithAuthentication(Long questionId, Long answerId, User loginUser) {
        Answer answer = answerRepository.findByIdAndQuestionIdAndDeleted(answerId, questionId, false)
                .orElseThrow(NotFoundException::new);
        if (!answer.matchesWriter(loginUser)) {
            throw new UnauthorizedAccessException("다른 사람의 답변을 수정하거나 삭제할 수 없습니다.");
        }
        return answer;
    }

    public void update(Long questionId, Long answerId, User loginUser, Answer updatedAnswer) {
        Answer answer = getWithAuthentication(questionId, answerId, loginUser);
        answer.updateAnswer(updatedAnswer);
        answerRepository.save(answer);
    }
}
