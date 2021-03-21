package com.codessquad.qna.domain.pagination;

import java.util.ArrayList;
import java.util.List;

public class PageDTO {

    private int startPage;
    private int endPage;
    private boolean prev;
    private boolean next;
    private int prevEndPage;
    private int nextStartPage;

    private int total;
    private Criteria cri;

    private List<PageNumber> numbers = new ArrayList<>();

    public PageDTO(Criteria cri, int total) {
        this.cri = cri;
        this.total = total;

        this.endPage = (int) (Math.ceil(cri.getPageNum() / 5.0)) * 5;
        this.startPage = this.endPage - 4;

        int realEnd = (int) (Math.ceil((total * 1.0) / cri.getAmount()));

        if (realEnd < this.endPage) {
            this.endPage = realEnd;
        }

        this.prev = this.startPage > 1;
        this.next = this.endPage < realEnd;

        int pageStartNumber = this.startPage;
        for (int i = 0; i <= this.endPage - this.startPage; i++) {
             numbers.add(new PageNumber(pageStartNumber++, cri.getPageNum()));
        }

        prevEndPage = startPage - 1;
        nextStartPage = endPage + 1;
    }

    public int getStartPage() {
        return startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public boolean isPrev() {
        return prev;
    }

    public boolean isNext() {
        return next;
    }

    public int getTotal() {
        return total;
    }

    public Criteria getCri() {
        return cri;
    }

    public List<PageNumber> getNumbers() {
        return numbers;
    }

    public int getNextStartPage() {
        return nextStartPage;
    }

    public int getPrevEndPage() {
        return prevEndPage;
    }
}
