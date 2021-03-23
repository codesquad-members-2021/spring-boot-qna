package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.Question;
import com.codessquad.qna.web.domain.QuestionRepository;
import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.exceptions.auth.UnauthorizedAccessException;
import com.codessquad.qna.web.exceptions.questions.QuestionNotFoundException;
import org.springframework.stereotype.Service;

import static com.codessquad.qna.web.utils.ExceptionConstants.CANNOT_MODIFY_ANOTHER_USERS_QUESTION;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void createQuestion(Question question, User writer) {
        question.setWriter(writer);
        questionRepository.save(question);
    }

    public Iterable<Question> questions() {
        return questionRepository.findAllByDeletedFalse();
    }

    public Question questionDetail(long id) {
        return questionRepository.findByIdAndDeletedFalse(id).orElseThrow(QuestionNotFoundException::new);
    }

    public Question verifyQuestionAndGet(User sessionUser, Long questionId) {
        Question currentQuestion = questionRepository.findByIdAndDeletedFalse(questionId)
                .orElseThrow(QuestionNotFoundException::new);
        verifyAuthorizedAccess(currentQuestion, sessionUser);
        return currentQuestion;
    }

    public Question modifyQuestion(User sessionUser, long questionId, String newTitle, String newContents) {
        Question question = verifyQuestionAndGet(sessionUser, questionId);
        question.update(newTitle, newContents);
        questionRepository.save(question);
        return question;
    }

    public void deleteQuestion(User sessionUser, long questionId) {
        Question currentQuestion = verifyQuestionAndGet(sessionUser, questionId);
        questionRepository.delete(currentQuestion);
    }

    private void verifyAuthorizedAccess(Question question, User loginUser) {
        if (!question.isMatchingWriter(loginUser)) {
            throw new UnauthorizedAccessException(CANNOT_MODIFY_ANOTHER_USERS_QUESTION);
        }
    }
}
