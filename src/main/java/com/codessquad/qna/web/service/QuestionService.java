package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.PageList;
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

    public PageList pageListByCurrentPage(long currentPage) {
        long numberOfQuestions = questionRepository.countAllByDeletedFalse();
        long lastPage = numberOfQuestions / PAGE_SIZE;

        long startPageOfCurrentBlock = calculateStartPageOfCurrentBlock(currentPage);
        long endPageOfCurrentBlock = calculateEndPageOfCurrentBlock(startPageOfCurrentBlock, lastPage);

        long endPageOfPrevBlock = calculateEndPageOfPrevBlock(startPageOfCurrentBlock);
        long startPageOfNextBlock = calculateStartPageOfNextBlock(endPageOfCurrentBlock, lastPage);

        return new PageList(startPageOfCurrentBlock, endPageOfCurrentBlock, endPageOfPrevBlock, startPageOfNextBlock);
    }

    private long calculateStartPageOfCurrentBlock(long currentPage) {
        return currentPage - (currentPage % BLOCK_SIZE);
    }

    private long calculateEndPageOfCurrentBlock(long startPageOfCurrentBlock, long lastPage) {
        long endPageOfCurrentBlock = startPageOfCurrentBlock + BLOCK_SIZE - 1;
        if (lastPage < endPageOfCurrentBlock) {
            endPageOfCurrentBlock = lastPage;
        }
        return endPageOfCurrentBlock;
    }

    private long calculateEndPageOfPrevBlock(long startPageOfCurrentBlock) {
        long endPageOfPrevBlock = PageList.NO_PAGE;
        if (startPageOfCurrentBlock != 0) {
            endPageOfPrevBlock = startPageOfCurrentBlock - 1;
        }
        return endPageOfPrevBlock;
    }

    private long calculateStartPageOfNextBlock(long endPageOfCurrentBlock, long lastPage) {
        long startPageOfNextBlock = PageList.NO_PAGE;
        if (endPageOfCurrentBlock < lastPage) {
            startPageOfNextBlock = endPageOfCurrentBlock + 1;
        }
        return startPageOfNextBlock;
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
