package com.codessquad.qna.service;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.CannotDeleteQuestionException;
import com.codessquad.qna.exception.IndexOutOfPageException;
import com.codessquad.qna.exception.NotAuthorizedException;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class QuestionService {
    private static final int PAGE_SIZE = 5;
    private static final int PAGE_LIST_SIZE = 5;
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question addQuestion(User user, String title, String contents) {
        return questionRepository.save(new Question(user, title, contents));
    }

    public Question updateQuestion(long questionId, String title, String contents, User tryToUpdate) {
        Question question = getQuestion(questionId);
        if (!question.isWriter(tryToUpdate)) {
            throw new NotAuthorizedException();
        }

        question.update(title, contents);
        return questionRepository.save(question);
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
    }

    public Page<Question> getQuestions(int pageNum) {
        if (pageNum <= 0) {
            throw new IndexOutOfPageException("페이지 번호가 1보다 작을 수 없습니다.");
        }

        Sort sort = Sort.by("createdDateTime").descending().and(Sort.by("id").descending());
        return questionRepository.findAll(PageRequest.of(pageNum - 1, PAGE_SIZE, sort));
    }

    public Question getQuestion(long id) {
        return questionRepository.findById(id).orElseThrow(() -> new NotFoundException(id + " 질문을 찾을 수 없습니다."));
    }

    public List<Integer> pageNumbers(int pageNum, int totalPageNum) {
        int startPageNum = (pageNum - 1) / PAGE_LIST_SIZE * PAGE_LIST_SIZE + 1;

        int lastPageNumCalculated = startPageNum + PAGE_LIST_SIZE - 1;
        int lastPageNum = lastPageNumCalculated < totalPageNum ? lastPageNumCalculated : totalPageNum;

        return IntStream.range(startPageNum, lastPageNum+1).boxed().collect(Collectors.toList());
    }

    public int prevPageNumber(List<Integer> pageNumbers) {
        if (pageNumbers != null && pageNumbers.size() > 0) {
            int firstPageNum = pageNumbers.get(0);
            if (firstPageNum <= 1) {
                return 1;
            }
            return firstPageNum - 1;
        }
        return 1;
    }

    public int nextPageNumber(List<Integer> pageNumbers, int totalPageNum) {
        if (pageNumbers != null && pageNumbers.size() > 0) {
            int lastPageNum = pageNumbers.get(pageNumbers.size() - 1);
            if (lastPageNum < totalPageNum) {
                return lastPageNum + 1;
            }
            return lastPageNum;
        }
        return 1;
    }
}
