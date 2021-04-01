package com.codessquad.qna.web.domain;

import com.codessquad.qna.web.exceptions.questions.PageOutOfBoundaryException;

import java.util.ArrayList;
import java.util.List;

public class PageList {
    public static final int NO_PAGE = -1;
    public static final int PAGE_SIZE = 15;
    public static final int BLOCK_SIZE = 5;
    private final List<Integer> pages;
    private final int endPageOfPrevBlock;
    private final int startPageOfNextBlock;

    public PageList(int currentPage, int numberOfQuestions) {
        pages = new ArrayList<>();
        currentPage -= 1;
        int lastPage = calculateLastPage(numberOfQuestions);
        int startPageOfCurrentBlock = calculateStartPageOfCurrentBlock(currentPage);
        int endPageOfCurrentBlock = calculateEndPageOfCurrentBlock(startPageOfCurrentBlock, lastPage);

        for (int i = startPageOfCurrentBlock; i <= endPageOfCurrentBlock; i++) {
            pages.add(i);
        }

        this.endPageOfPrevBlock = calculateEndPageOfPrevBlock(startPageOfCurrentBlock);
        this.startPageOfNextBlock = calculateStartPageOfNextBlock(endPageOfCurrentBlock, lastPage);
    }

    public static void verifyPageNumberIsInBoundary(int pageNumber, int numberOfQuestions) {
        int lastPage = PageList.calculateLastPage(numberOfQuestions);
        if (pageNumber <= 0 || pageNumber > lastPage) {
            throw new PageOutOfBoundaryException();
        }
    }

    public boolean hasPrevBlock() {
        return endPageOfPrevBlock != NO_PAGE;
    }

    public boolean hasNextBlock() {
        return startPageOfNextBlock != NO_PAGE;
    }

    public static int calculateLastPage(int numberOfQuestions) {
        return numberOfQuestions / PAGE_SIZE + 1;
    }

    private int calculateStartPageOfCurrentBlock(int currentPage) {
        return currentPage - (currentPage % BLOCK_SIZE) + 1;
    }

    private int calculateEndPageOfCurrentBlock(int startPageOfCurrentBlock, int lastPage) {
        int endPageOfCurrentBlock = startPageOfCurrentBlock + BLOCK_SIZE - 1;
        if (lastPage < endPageOfCurrentBlock) {
            endPageOfCurrentBlock = lastPage;
        }
        return endPageOfCurrentBlock;
    }

    private int calculateEndPageOfPrevBlock(int startPageOfCurrentBlock) {
        int endPageOfPrevBlock = PageList.NO_PAGE;
        if (startPageOfCurrentBlock > 1) {
            endPageOfPrevBlock = startPageOfCurrentBlock - 1;
        }
        return endPageOfPrevBlock;
    }

    private int calculateStartPageOfNextBlock(int endPageOfCurrentBlock, int lastPage) {
        int startPageOfNextBlock = PageList.NO_PAGE;
        if (endPageOfCurrentBlock < lastPage) {
            startPageOfNextBlock = endPageOfCurrentBlock + 1;
        }
        return startPageOfNextBlock;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public int getEndPageOfPrevBlock() {
        return endPageOfPrevBlock;
    }

    public int getStartPageOfNextBlock() {
        return startPageOfNextBlock;
    }
}
