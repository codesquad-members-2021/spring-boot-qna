package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.Question;
import com.codessquad.qna.web.domain.QuestionRepository;
import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.exceptions.InvalidEntityException;
import com.codessquad.qna.web.exceptions.auth.UnauthorizedAccessException;
import com.codessquad.qna.web.exceptions.questions.QuestionNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.codessquad.qna.web.utils.ExceptionConstants.CANNOT_MODIFY_OR_DELETE_ANOTHER_USERS_QUESTION;
import static com.codessquad.qna.web.utils.ExceptionConstants.EMPTY_FIELD_IN_QUESTION_ENTITY;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void createQuestion(Question question, User writer) {
        verifyQuestionEntityIsValid(question);
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
        Question question = questionRepository.findByIdAndDeletedFalse(questionId)
                .orElseThrow(QuestionNotFoundException::new);
        verifyWriterIsQuestionOwner(question, loginUser);
        verifyQuestionEntityIsValid(newQuestion);
        question.update(newQuestion);
        questionRepository.save(question);
        return question;
    }

    @Transactional
    public void deleteQuestion(User loginUser, long questionId) {
        Question question = questionRepository.findByIdAndDeletedFalse(questionId)
                .orElseThrow(QuestionNotFoundException::new);
        verifyWriterIsQuestionOwner(question, loginUser);
        question.delete();
    }

    private void verifyQuestionEntityIsValid(Question question) {
        if (!question.isValid()) {
            throw new InvalidEntityException(EMPTY_FIELD_IN_QUESTION_ENTITY);
        }
    }

    public void verifyWriterIsQuestionOwner(Question question, User writer) {
        if (!question.isMatchingWriter(writer)) {
            throw new UnauthorizedAccessException(CANNOT_MODIFY_OR_DELETE_ANOTHER_USERS_QUESTION);
        }
    }
}
