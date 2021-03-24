package com.codessquad.qna.service;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.repository.AnswerRepository;
import com.codessquad.qna.repository.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.codessquad.qna.util.HttpSessionUtils.checkAccessibleSessionUser;

@Service
public class QuestionService {
    private final Logger logger = LoggerFactory.getLogger(QuestionService.class);
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
    }

    public void addQuestion(Question newQuestion, User user) {
        newQuestion.setWriter(user);
        questionRepository.save(newQuestion);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAllByAndDeletedFalse();
    }

    public Question getOneById(Long id) {
        return questionRepository.findByQuestionIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 질문입니다."));
    }

    public void updateInfo(Question targetQuestion, Question newQuestionInfo, User sessionUser) {

        checkAccessibleSessionUser(sessionUser, targetQuestion);

        targetQuestion.updateQuestionInfo(newQuestionInfo);
        questionRepository.save(targetQuestion);
    }

    public void remove(User sessionUser, Question question) {

        checkAccessibleSessionUser(sessionUser, question);

        question.deleted();
        questionRepository.save(question);
    }
}
