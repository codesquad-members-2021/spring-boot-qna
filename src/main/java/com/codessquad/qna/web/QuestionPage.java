package com.codessquad.qna.web;

import com.codessquad.qna.web.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class QuestionPage {
    private final int COUNT_NUMBERS_TO_SHOW = 5;
    private final Pageable pageable;
    private int previous;
    private int next;
    private Page<Question> questions;
    private List<Integer> pageNumbers = new ArrayList<>(COUNT_NUMBERS_TO_SHOW);

    public QuestionPage(Pageable pageable, Page<Question> questionsByPage) {
        this.pageable = pageable;
        this.questions = questionsByPage;
        this.previous = questionsByPage.previousOrFirstPageable().getPageNumber();
        if (questionsByPage.hasNext()) {
            this.next = questionsByPage.nextPageable().getPageNumber();
        } else {
            this.next = questionsByPage.getNumber();

        }
        this.pageNumbers = createPageNumbers();
    }

    private List<Integer> createPageNumbers() {
        int currentPage = questions.getNumber();
        int totalPages = questions.getTotalPages();
        int startNumber = (currentPage / COUNT_NUMBERS_TO_SHOW) * COUNT_NUMBERS_TO_SHOW;
        int endNumber = startNumber + COUNT_NUMBERS_TO_SHOW - 1;

        for (int i = startNumber; i <= endNumber; i++) {
            pageNumbers.add(i);
            if (i == totalPages - 1) {
                break;
            }
        }
        return pageNumbers;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public int getPrevious() {
        return previous;
    }

    public int getNext() {
        return next;
    }

    public Page<Question> getQuestions() {
        return questions;
    }

    public List<Integer> getPageNumbers() {
        return pageNumbers;
    }
}
