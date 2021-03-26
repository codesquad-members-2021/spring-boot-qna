package com.codessquad.qna.util;

import java.util.ArrayList;
import java.util.List;

public class PageUtil {
    private static final int PAGE_COUNT = 6;

    private int previousPage;
    private List<Integer> pages;
    private int nextPage;
    private int max;
    private int currentPage;

    public PageUtil(int currentPage, int totalPages) {
        this.previousPage = 0;
        this.currentPage = currentPage;
        this.max = totalPages;
        this.pages = createList();
        this.nextPage = createNext();
    }

    private List<Integer> createList() {
        List<Integer> list = new ArrayList<>();

        if (max <= 5) {
            loopList(list, 1, max);
            return list;
        }

        if (currentPage / PAGE_COUNT < 1) {
            loopList(list, 1, 5);
            return list;
        }

        int start = PAGE_COUNT;
        int last = start + 4;
        if (last > max) {
            last = max;
        }
        loopList(list, start, last);
        this.previousPage = start - 1;

        return list;
    }

    private void loopList(List<Integer> list, int start, int last) {
        for (int pageNumber = start; pageNumber <= last; pageNumber++) {
            list.add(pageNumber);
        }
    }

    private int createNext() {
        int next = pages.get(pages.size() - 1);
        if (next == max) {
            return 0;
        }
        return next + 1;
    }

    public int getPreviousPage() {
        return previousPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public List<Integer> getPages() {
        return pages;
    }

}
