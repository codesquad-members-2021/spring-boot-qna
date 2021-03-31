package com.codessquad.qna.domain.pagination;

public class Criteria {

    private int pageNum; // 현재 page 번호
    private int size;    // page당 게시물 수, 15로 고정

    public Criteria(int pageNum) {
        this.pageNum = pageNum;
        this.size = 15;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "Criteria{" +
                "pageNum=" + pageNum +
                ", size=" + size +
                '}';
    }
}
