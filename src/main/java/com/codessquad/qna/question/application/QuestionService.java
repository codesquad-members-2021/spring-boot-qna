package com.codessquad.qna.question.application;

import com.codessquad.qna.answer.domain.Answer;
import com.codessquad.qna.answer.domain.AnswerRepository;
import com.codessquad.qna.answer.dto.AnswerRequest;
import com.codessquad.qna.answer.dto.AnswerResponse;
import com.codessquad.qna.answer.exception.AnswerDeletedException;
import com.codessquad.qna.answer.exception.AnswerNotFoundException;
import com.codessquad.qna.question.domain.Question;
import com.codessquad.qna.question.domain.QuestionRepository;
import com.codessquad.qna.question.dto.QuestionRequest;
import com.codessquad.qna.question.dto.QuestionResponse;
import com.codessquad.qna.question.exception.QuestionDeletedException;
import com.codessquad.qna.question.exception.QuestionNotDeletableException;
import com.codessquad.qna.question.exception.QuestionNotFoundException;
import com.codessquad.qna.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
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

    @Transactional(readOnly = true)
    public List<QuestionResponse> getList(Pageable pageable) {
        List<QuestionResponse> questionResponses = new ArrayList<>();
        for (Question question : questionRepository.findAllByDeletedIsFalse(pageable)) {
            questionResponses.add(QuestionResponse.from(question));
        }
        return questionResponses;
    }

    @Transactional(readOnly = true)
    public QuestionResponse get(Long id) {
        Question question = getQuestionFromRepository(id);
        return QuestionResponse.from(question);
    }

    public QuestionResponse update(Long id, QuestionRequest questionRequest, User writer) {
        Question question = getQuestionFromRepository(id);
        question.update(questionRequest.toQuestion(writer));
        return QuestionResponse.from(question);
    }

    public QuestionResponse delete(Long id) {
        Question question = getQuestionFromRepository(id);
        if (!question.isDeletable()) {
            throw new QuestionNotDeletableException();
        }
        question.delete();
        return QuestionResponse.from(question);
    }

    public User getWriter(Long id) {
        return getQuestionFromRepository(id).getWriter();
    }

    public AnswerResponse addAnswer(Long questionId, AnswerRequest answerRequest, User writer) {
        Question question = getQuestionFromRepository(questionId);
        Answer answer = answerRepository.save(answerRequest.toAnswer(question, writer));
        question.addCountOfAnswer();
        return AnswerResponse.from(answer);
    }

    public AnswerResponse deleteAnswer(Long id) {
        Answer answer = getAnswerFromRepository(id);
        answer.delete();
        Question question = answer.getQuestion();
        question.deleteCountOfAnswer();
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

    private Answer getAnswerFromRepository(Long id) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new AnswerNotFoundException(id));
        if (answer.isDeleted()) {
            throw new AnswerDeletedException(id);
        }
        return answer;
    }

    public User getAnswerWriter(Long id) {
        return getAnswerFromRepository(id).getWriter();
    }
}
