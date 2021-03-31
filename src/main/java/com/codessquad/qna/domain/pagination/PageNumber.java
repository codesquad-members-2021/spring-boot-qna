package com.codessquad.qna.domain.pagination;

public class PageNumber {
    private int page;
    private boolean active; // 페이지 목록에서 현재 페이지와 일치하는 페이지일 경우 true

    public PageNumber(int page, int currentPageNum) {
        this.page = page;
        this.active = page == currentPageNum;
    }
    public int getPage() {
        return page;
    }

    public boolean getActive() {
        return active;
    }
}
