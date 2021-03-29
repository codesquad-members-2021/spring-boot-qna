package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.Question;
import com.codessquad.qna.web.domain.QuestionRepository;
import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.exceptions.questions.QuestionNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void createQuestion(Question question, User writer) {
        question.verifyQuestionEntityIsValid();
        question.setWriter(writer);
        questionRepository.save(question);
    }

    public Iterable<Question> questions() {
        return questionRepository.findAllByDeletedFalse();
    }

    public Question questionDetail(long id) {
        return questionRepository.findByIdAndDeletedFalse(id).orElseThrow(QuestionNotFoundException::new);
    }

    public Question modifyQuestion(User loginUser, long questionId, Question newQuestion) {
        newQuestion.verifyQuestionEntityIsValid();
        Question question = questionRepository.findByIdAndDeletedFalse(questionId)
                .orElseThrow(QuestionNotFoundException::new);
        question.verifyIsQuestionOwner(loginUser);
        question.update(newQuestion);
        questionRepository.save(question);
        return question;
    }

    @Transactional
    public void deleteQuestion(User loginUser, long questionId) {
        Question question = questionRepository.findByIdAndDeletedFalse(questionId)
                .orElseThrow(QuestionNotFoundException::new);
        question.verifyIsQuestionOwner(loginUser);
        question.delete();
    }
}
