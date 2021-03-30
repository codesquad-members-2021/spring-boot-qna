package com.codessquad.qna.web.service;

import com.codessquad.qna.web.QuestionPage;
import com.codessquad.qna.web.domain.Question;
import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.exception.IllegalAccessException;
import com.codessquad.qna.web.exception.IllegalEntityIdException;
import com.codessquad.qna.web.repository.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void postQuestion(Question question, User user) {
        question.setWriter(user);
        questionRepository.save(question);
    }

    public Question findQuestion(long id) {
        return questionRepository
                .findById(id)
                .orElseThrow(() -> new IllegalEntityIdException("id(번호)에 해당하는 질문이 없습니다"));
    }

    public void updateQuestion(long id, User user, Question question) {
        Question originQuestion = checkAndGetQuestion(id, user);
        originQuestion.update(question);
        questionRepository.save(originQuestion);
    }

    public void deleteQuestion(long id, User user) {
        Question originQuestion = checkAndGetQuestion(id, user);
        questionRepository.delete(originQuestion);
    }

    public Question checkAndGetQuestion(long id, User user) {
        Question question = findQuestion(id);
        checkSameWriter(question, user);
        return question;
    }

    private void checkSameWriter(Question question, User user) {
        if (!question.isSameWriter(user)) {
            throw new IllegalAccessException();
        }
    }

    public Page<Question> getQuestionsByPage(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    public QuestionPage getQuestionPage(Pageable pageable) {
        return new QuestionPage(pageable, getQuestionsByPage(pageable));
    }
}
