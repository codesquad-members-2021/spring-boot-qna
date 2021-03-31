package com.codessquad.qna.domain.pagination;

import com.codessquad.qna.domain.question.Question;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class PageDTO {
    private Page<Question> questions;
    /**
     * 현재 페이지가 포함된 페이지 목록.
     * 현재 페이지가 3이고 총 페이지가 10페이지로 가정하면,
     * 페이지 목록은 [ 1 2 3 4 5 >>] 이 되야하고 이때 numbers는 1, 2, 3, 4, 5를 가진다.
     */
    private List<PageNumber> pages = new ArrayList<>();

    public PageDTO(Page<Question> questions) {
        this.questions = questions;
        int pageStartNumber = getStartPage();
        for (int i = 0; i <= getEndPage() - getStartPage(); i++) { // 페이지 목록에서 startPage부터 endPage까지 저장한다.
            pages.add(new PageNumber(pageStartNumber++, questions.getNumber() + 1));
        }
    }

    public int getStartPage() {
        int endPage = (int) (Math.ceil((questions.getNumber() + 1) / 5.0)) * 5; // 5는 페이지 목록에서 출력할 페이지 개수 (n.0, n)
        int startPage = endPage - 4; // 시작페이지는 마지막 페이지에서 (페이지 목록에서 출력할 페이지 개수 - 1)를 뺸다.
        return startPage;
    }

    public int getEndPage() {
        int endPage = (int) (Math.ceil((questions.getNumber() + 1) / 5.0)) * 5; // 5는 페이지 목록에서 출력할 페이지 개수 (n.0, n)
        int realEnd = (int) (Math.ceil((questions.getTotalElements() * 1.0) / questions.getSize())); // 실제 데이터를 바탕으로 한 진짜 끝 페이지
        if (realEnd < endPage) { // endPage는 5의 배수로 고정되어있다. 페이지 목록의 realEnd(실제)가 endPage(5의 배수)보다 작다면,
            endPage = realEnd;   // realEnd가 3이라면 endPage [ 1 2 3 4 5 ]로 표시되는 것을 [ 1 2 3 ]로 바꿔줘야한다.
        }
        return endPage;
    }

    public boolean isPrev() { // 페이지 목록의 "<<" 버튼 활성화 여부
        return getStartPage() > 1;     // startPage는 1, 6, 7 ...로 5씩 증가한다. 6부터는 (2번째 페이지목록) prev활성화
    }

    public boolean isNext() { // 페이지 목록의 ">>" 버튼 활성화 여부
        int realEnd = (int) (Math.ceil((questions.getTotalElements() * 1.0) / questions.getSize())); // 실제 데이터를 바탕으로 한 진짜 끝 페이지
        return getEndPage() < realEnd; // 현재페이지 목록의 endPage보다 실제 마지막 페이지(realEnd)가 크다면 다음 페이지목록이 존재한다.
    }

    public long getTotal() {
        return questions.getTotalElements();
    }

    public List<PageNumber> getPages() {
        return pages;
    }

    public int getNextStartPage() {
        return getEndPage() + 1;
    }

    public int getPrevEndPage() {
        return getStartPage() - 1;
    }
}
