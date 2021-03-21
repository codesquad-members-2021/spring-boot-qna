package com.codessquad.qna.domain.pagination;

import java.util.ArrayList;
import java.util.List;

public class PageDTO {
    private int startPage;     // 페이지 목록의 시작 페이지
    private int endPage;       // 페이지 목록의 마지막 페이지
    private boolean prev;      // 페이지 목록의 "<<" 버튼 활성화 여부
    private boolean next;      // 페이지 목록의 ">>" 버튼 활성화 여부
    private int prevEndPage;   // "<<" 버튼 클릭시 startPage 이전 페이지로 이동, [ << 6 7 8 ] 일 경우 5로 이동
    private int nextStartPage; // ">>" 버튼 클릭시 endPage 다음 페이지로 이동, [ 1 2 3 4 5 >> ] 일 경우 6으로 이동

    private int total;         // 화면에 출력할 수 있는(삭제되지 않은) 게시물의 총 개수
    private Criteria cri;      // 현재 페이지(pageNum)과 한 페이지에서 보여줄 게시물 개수(size) 정보를 가짐

    /**
     * 현재 페이지가 포함된 페이지 목록.
     * 현재 페이지가 3이고 총 페이지가 10페이지로 가정하면,
     * 페이지 목록은 [ 1 2 3 4 5 >>] 이 되야하고 이때 numbers는 1, 2, 3, 4, 5를 가진다.
     */
    private List<PageNumber> numbers = new ArrayList<>();

    public PageDTO(Criteria cri, int total) {
        this.cri = cri;
        this.total = total;

        this.endPage = (int) (Math.ceil(cri.getPageNum() / 5.0)) * 5; // 5는 페이지 목록에서 출력할 페이지 개수 (n.0, n)
        this.startPage = this.endPage - 4; // 시작페이지는 마지막 페이지에서 (페이지 목록에서 출력할 페이지 개수 - 1)를 뺸다.

        int realEnd = (int) (Math.ceil((total * 1.0) / cri.getSize())); // 실제 데이터를 바탕으로 한 진짜 끝 페이지

        if (realEnd < this.endPage) { // endPage는 5의 배수로 고정되어있다. 페이지 목록의 realEnd(실제)가 endPage(5의 배수)보다 작다면,
            this.endPage = realEnd;   // realEnd가 3이라면 endPage [ 1 2 3 4 5 ]로 표시되는 것을 [ 1 2 3 ]로 바꿔줘야한다.
        }

        this.prev = this.startPage > 1;     // startPage는 1, 6, 7 ...로 5씩 증가한다. 6부터는 (2번째 페이지목록) prev활성화
        this.next = this.endPage < realEnd; // 현재페이지 목록의 endPage보다 실제 마지막 페이지(realEnd)가 크다면 다음 페이지목록이 존재한다.

        int pageStartNumber = this.startPage;
        for (int i = 0; i <= this.endPage - this.startPage; i++) { // 페이지 목록에서 startPage부터 endPage까지 저장한다.
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
