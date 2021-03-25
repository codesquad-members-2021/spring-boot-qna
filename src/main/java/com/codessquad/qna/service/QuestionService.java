package com.codessquad.qna.service;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.CannotDeleteQuestionException;
import com.codessquad.qna.exception.NotAuthorizedException;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.exception.SaveFailedException;
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

    public Question addQuestion(User user, String title, String contents) {
        Question added = questionRepository.save(new Question(user, title, contents));
        if (added == null) {
            throw new SaveFailedException(Question.class);
        }
        return added;
    }

    public Question updateQuestion(long questionId, String title, String contents, User tryToUpdate) {
        Question question = getQuestion(questionId);
        if (!question.isWriter(tryToUpdate)) {
            throw new NotAuthorizedException();
        }

        question.update(title, contents);
        Question updated = questionRepository.save(question);
        if (updated == null) {
            throw new SaveFailedException(Question.class);
        }
        return updated;
    }

    public Question deleteQuestion(long questionId, User tryToDelete) {
        Question question = getQuestion(questionId);
        if (!question.isWriter(tryToDelete)) {
            throw new NotAuthorizedException();
        }
        if (!question.canDeleted()) {
            throw new CannotDeleteQuestionException();
        }

        question.delete();
        Question deleted = questionRepository.save(question);
        if (deleted == null) {
            throw new SaveFailedException(Question.class);
        }
        return deleted;
    }

    public List<Question> getQuestions() {
        return questionRepository.findAllByAndDeletedFalse();
    }

    public Question getQuestion(long id) {
        return questionRepository.findById(id).orElseThrow(() -> new NotFoundException(id + " 질문을 찾을 수 없습니다."));
    }
}
