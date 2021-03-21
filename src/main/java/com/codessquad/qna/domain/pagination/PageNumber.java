package com.codessquad.qna.domain.pagination;

public class PageNumber {
    private int number;
    private boolean active; // 페이지 목록에서 현재 페이지와 일치하는 페이지일 경우 true

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
