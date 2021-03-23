package com.codessquad.qna.service;

import com.codessquad.qna.entity.Question;
import com.codessquad.qna.entity.User;
import com.codessquad.qna.exception.CannotDeleteQuestionException;
import com.codessquad.qna.exception.NotAuthorizedException;
import com.codessquad.qna.exception.QuestionNotFoundException;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void addQuestion(User user, String title, String contents) {
        questionRepository.save(new Question(user, title, contents));
    }

    public void updateQuestion(long questionId, String title, String contents, User tryToUpdate) {
        Question question = getQuestion(questionId);
        if (question.isWriter(tryToUpdate)) {
            question.update(title, contents);
            questionRepository.save(question);
            return;
        }
        throw new NotAuthorizedException();
    }

    public void deleteQuestion(long questionId, User tryToDelete) {
        Question question = getQuestion(questionId);
        if (!question.isWriter(tryToDelete)) {
            throw new NotAuthorizedException();
        }
        if (!question.canDeleted()) {
            throw new CannotDeleteQuestionException();
        }

        question.delete();
        questionRepository.save(question);
        return;

    }

    public List<Question> getQuestions() {
        return questionRepository.findAllByAndDeletedFalse();
    }

    public Question getQuestion(long id) {
        return questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);
    }
}
