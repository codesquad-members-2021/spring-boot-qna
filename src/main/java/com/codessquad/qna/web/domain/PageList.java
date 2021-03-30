package com.codessquad.qna.web.domain;

import java.util.ArrayList;
import java.util.List;

public class PageList {
    public static final long NO_PAGE = -1;
    public static final int PAGE_SIZE = 15;
    public static final int BLOCK_SIZE = 5;
    private final List<Long> pages;
    private final long endPageOfPrevBlock;
    private final long startPageOfNextBlock;

    public PageList(long currentPage, long numberOfQuestions) {
        pages = new ArrayList<>();
        long lastPage = calculateLastPage(numberOfQuestions);
        long startPageOfCurrentBlock = calculateStartPageOfCurrentBlock(currentPage);
        long endPageOfCurrentBlock = calculateEndPageOfCurrentBlock(startPageOfCurrentBlock, lastPage);

        for (long i = startPageOfCurrentBlock; i <= endPageOfCurrentBlock; i++) {
            pages.add(i + 1);
        }

        this.endPageOfPrevBlock = calculateEndPageOfPrevBlock(startPageOfCurrentBlock) + 1;
        this.startPageOfNextBlock = calculateStartPageOfNextBlock(endPageOfCurrentBlock, lastPage) + 1;
    }

    public boolean hasPrevBlock() {
        return endPageOfPrevBlock != NO_PAGE;
    }

    public boolean hasNextBlock() {
        return startPageOfNextBlock != NO_PAGE;
    }

    public long calculateLastPage(long numberOfQuestions) {
        return numberOfQuestions / PAGE_SIZE;
    }

    public long calculateStartPageOfCurrentBlock(long currentPage) {
        return currentPage - (currentPage % BLOCK_SIZE);
    }

    public long calculateEndPageOfCurrentBlock(long startPageOfCurrentBlock, long lastPage) {
        long endPageOfCurrentBlock = startPageOfCurrentBlock + BLOCK_SIZE - 1;
        if (lastPage < endPageOfCurrentBlock) {
            endPageOfCurrentBlock = lastPage;
        }
        return endPageOfCurrentBlock;
    }

    public long calculateEndPageOfPrevBlock(long startPageOfCurrentBlock) {
        long endPageOfPrevBlock = PageList.NO_PAGE;
        if (startPageOfCurrentBlock != 0) {
            endPageOfPrevBlock = startPageOfCurrentBlock - 1;
        }
        return endPageOfPrevBlock;
    }

    public long calculateStartPageOfNextBlock(long endPageOfCurrentBlock, long lastPage) {
        long startPageOfNextBlock = PageList.NO_PAGE;
        if (endPageOfCurrentBlock < lastPage) {
            startPageOfNextBlock = endPageOfCurrentBlock + 1;
        }
        return startPageOfNextBlock;
    }

    public List<Long> getPages() {
        return pages;
    }

    public long getEndPageOfPrevBlock() {
        return endPageOfPrevBlock;
    }

    public long getStartPageOfNextBlock() {
        return startPageOfNextBlock;
    }
}
