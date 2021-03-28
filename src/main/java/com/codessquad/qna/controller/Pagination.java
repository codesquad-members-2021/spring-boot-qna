package com.codessquad.qna.controller;

import java.util.ArrayList;
import java.util.List;

public class Pagination {

    private static final int PAGE = 5;

    private final Integer prev;
    private final Integer next;
    private final List<Integer> pages;

    private Pagination(Integer prev, Integer next, List<Integer> pages) {
        this.prev = prev;
        this.next = next;
        this.pages = pages;
    }

    public static Pagination of(int pageNumber, int totalPages) {
        if (pageNumber < 1 || pageNumber > totalPages) {
            throw new IllegalArgumentException();
        }

        int pageIndex = pageNumber - 1;
        int pageStart = (pageIndex / PAGE) * PAGE;
        int pageEnd = ((pageIndex / PAGE) + 1) * PAGE;
        boolean nextPages = true;
        Integer prev = null;
        Integer next = null;
        List<Integer> pages = new ArrayList<>(PAGE);
        if (pageEnd > totalPages - 1) {
            pageEnd = pageIndex + 1;
            nextPages = false;
        }
        if (pageStart != 0) {
            prev = pageStart;
        }
        for (int i = pageStart; i < pageEnd; i++) {
            pages.add(i + 1);
        }
        if (nextPages) {
            next = pageEnd + 1;
        }
        return new Pagination(prev, next, pages);
    }

    public Integer getPrev() {
        return prev;
    }

    public Integer getNext() {
        return next;
    }

    public List<Integer> getPages() {
        return pages;
    }
}
