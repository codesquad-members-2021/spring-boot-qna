package com.codessquad.qna.service;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.repository.AnswerRepository;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.codessquad.qna.util.HttpSessionUtils.checkAccessibleSessionUser;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
    }

    public Question addQuestion(Question newQuestion, User user) {
        newQuestion.setWriter(user);
        return questionRepository.save(newQuestion);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAllByAndDeletedFalse();
    }

    public Question getOneById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 질문입니다."));
    }

    public Question update(Question targetQuestion, Question newQuestionInfo, User sessionUser) {
        checkAccessibleSessionUser(sessionUser, targetQuestion);

        targetQuestion.updateQuestionInfo(newQuestionInfo);
        return questionRepository.save(targetQuestion);
    }

    public Question remove(User sessionUser, Question question) {
        checkAccessibleSessionUser(sessionUser, question);

        question.deleted();
        return questionRepository.save(question);
    }
}
