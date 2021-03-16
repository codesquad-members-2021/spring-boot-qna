package com.codessquad.qna.question.application;

import com.codessquad.qna.question.domain.Question;
import com.codessquad.qna.question.domain.QuestionRepository;
import com.codessquad.qna.question.dto.QuestionRequest;
import com.codessquad.qna.question.dto.QuestionResponse;
import com.codessquad.qna.user.domain.User;
import com.codessquad.qna.user.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    private QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public QuestionResponse saveQuestion(QuestionRequest questionRequest, User writer) {
        Question question = questionRepository.save(questionRequest.toQuestion(writer));
        return QuestionResponse.of(question);
    }

    public List<QuestionResponse> getQuestions() {
        List<QuestionResponse> questionResponses = new ArrayList<>();
        for (Question question : questionRepository.findAll()) {
            questionResponses.add(QuestionResponse.of(question));
        }
        return questionResponses;
    }

    public QuestionResponse getQuestion(Long id) {
        Question question = getQuestionFromRepository(id);
        return QuestionResponse.of(question);
    }

    public QuestionResponse updateQuestion(Long id, QuestionRequest questionRequest, User writer) {
        Question question = getQuestionFromRepository(id);
        question.update(questionRequest.toQuestion(writer));
        questionRepository.save(question); // HELP: 이유를 모르겠지만 dirty checking 이 동작하지 않는다.
        return QuestionResponse.of(question);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    public Long getWriterId(Long id) {
        return getQuestionFromRepository(id)
                .getWriter()
                .getId();
    }

    private Question getQuestionFromRepository(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("존재하지 않는 질문입니다. id: %d", id)));
    }
}
