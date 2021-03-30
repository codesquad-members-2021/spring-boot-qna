package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.Question;
import com.codessquad.qna.web.domain.QuestionRepository;
import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.exceptions.questions.QuestionNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private static final int PAGE_SIZE = 5;
    private static final int BLOCK_SIZE = 5;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void createQuestion(Question question, User writer) {
        question.verifyQuestionEntityIsValid();
        question.setWriter(writer);
        questionRepository.save(question);
    }

    public Iterable<Question> questions(int pageNumber) {
        return questionRepository.findAllByDeleted(false, PageRequest.of(pageNumber, PAGE_SIZE));
    }

    public List<Long> pageList(long currentPage) {
        List<Long> list = new ArrayList<>();
        long startPage = currentPage - (currentPage % BLOCK_SIZE);
        long endPage = startPage + BLOCK_SIZE - 1;
        long numberOfQuestions = questionRepository.countAllByDeletedFalse();
        if (numberOfQuestions / PAGE_SIZE < endPage) {
            endPage = numberOfQuestions / PAGE_SIZE;
        }
        for (long i = startPage; i <= endPage; i++) {
            list.add(i);
        }
        return list;
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
