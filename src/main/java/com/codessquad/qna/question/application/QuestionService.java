package com.codessquad.qna.question.application;

import com.codessquad.qna.question.domain.Question;
import com.codessquad.qna.question.domain.QuestionRepository;
import com.codessquad.qna.question.dto.QuestionRequest;
import com.codessquad.qna.question.dto.QuestionResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuestionService {
    private QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public QuestionResponse saveQuestion(QuestionRequest questionRequest) {
        Question question = questionRepository.save(questionRequest.toQuestion());
        return QuestionResponse.of(question);
    }

    @Transactional(readOnly = true)
    public List<QuestionResponse> getQuestions() {
        return questionRepository.findAll()
                .stream()
                .map(QuestionResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public QuestionResponse getQuestion(Long id) {
        // TODO: 조회 실패에 대한 예외처리
        Question question = questionRepository.getOne(id);
        return QuestionResponse.of(question);
    }
}
