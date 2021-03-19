package com.codessquad.qna.question.application;

import com.codessquad.qna.answer.domain.Answer;
import com.codessquad.qna.answer.domain.AnswerRepository;
import com.codessquad.qna.answer.dto.AnswerRequest;
import com.codessquad.qna.answer.dto.AnswerResponse;
import com.codessquad.qna.question.domain.Question;
import com.codessquad.qna.question.domain.QuestionRepository;
import com.codessquad.qna.question.dto.QuestionRequest;
import com.codessquad.qna.question.dto.QuestionResponse;
import com.codessquad.qna.question.exception.QuestionDeletedException;
import com.codessquad.qna.question.exception.QuestionNotFoundException;
import com.codessquad.qna.user.domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public QuestionService(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public QuestionResponse save(QuestionRequest questionRequest, User writer) {
        Question question = questionRepository.save(questionRequest.toQuestion(writer));
        return QuestionResponse.from(question);
    }

    public List<QuestionResponse> getQuestions() {
        List<QuestionResponse> questionResponses = new ArrayList<>();
        for (Question question : questionRepository.findAllByDeletedIsFalse()) {
            questionResponses.add(QuestionResponse.from(question));
        }
        return questionResponses;
    }

    public QuestionResponse getQuestion(Long id) {
        Question question = getQuestionFromRepository(id);
        return QuestionResponse.from(question);
    }

    public QuestionResponse updateQuestion(Long id, QuestionRequest questionRequest, User writer) {
        Question question = getQuestionFromRepository(id);
        question.update(questionRequest.toQuestion(writer));
        questionRepository.save(question);
        return QuestionResponse.from(question);
    }

    public void deleteQuestion(Long id) {
        Question question = getQuestionFromRepository(id);
        question.delete();
        questionRepository.save(question);
    }

    public User getWriter(Long id) {
        return getQuestionFromRepository(id).getWriter();
    }

    public AnswerResponse addAnswer(Long questionId, AnswerRequest answerRequest, User writer) {
        Question question = getQuestionFromRepository(questionId);
        Answer answer = answerRepository.save(answerRequest.toAnswer(question, writer));
        return AnswerResponse.from(answer);
    }

    private Question getQuestionFromRepository(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));
        if (question.isDeleted()) {
            throw new QuestionDeletedException(id);
        }
        return question;
    }
}
