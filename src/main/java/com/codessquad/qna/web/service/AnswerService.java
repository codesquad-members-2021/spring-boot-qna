package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.Answer;
import com.codessquad.qna.web.domain.Question;
import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.exception.IllegalAccessException;
import com.codessquad.qna.web.exception.IllegalEntityIdException;
import com.codessquad.qna.web.repository.AnswerRepository;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionService questionService;

    public AnswerService(AnswerRepository answerRepository, QuestionService questionService) {
        this.answerRepository = answerRepository;
        this.questionService = questionService;
    }

    public Answer postAnswer(User writer, long questionId, String contents) {
        Question question = questionService.findQuestion(questionId);
        Answer answer = new Answer(writer, question, contents);
        question.upCountOfAnswer();
        return answerRepository.save(answer);
    }

    public void deleteAnswer(long id, User user) {
        Answer answer = findAnswer(id);
        checkWriter(answer, user);
        answer.getQuestion().downCountOfAnswer();
        answerRepository.delete(answer);
    }

    public Answer findAnswer(long id) {
        return answerRepository
                .findById(id)
                .orElseThrow(() -> new IllegalEntityIdException("id(번호)에 해당하는 답변이 없습니다"));
    }

    private void checkWriter(Answer answer, User user) {
        if (!answer.isSameWriter(user)) {
            throw new IllegalAccessException("자신의 답변만 수정/삭제할 수 있습니다");
        }
    }
}
