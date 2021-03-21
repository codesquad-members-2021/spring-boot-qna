package com.codessquad.qna.domain.pagination;

public class PageNumber {
    private int number;
    private boolean active;

    public PageNumber(int number, int currentPageNum) {
        this.number = number;
        this.active = number == currentPageNum;
    }
    public int getNumber() {
        return number;
    }

    public boolean getActive() {
        return active;
    }
}
