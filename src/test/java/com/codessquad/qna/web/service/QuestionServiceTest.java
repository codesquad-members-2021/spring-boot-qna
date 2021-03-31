package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.PageList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class QuestionServiceTest {
    @Autowired
    private QuestionService questionService;

    @Test
    @DisplayName("페이지번호를 이용해서 페이지리스트를 요청하면, 올바른 범위의 페이지리스트가 반환되어야 합니다")
    void testPageListIsOk() {
        testPageList(1, 1, 5, PageList.NO_PAGE, 6);
        testPageList(3, 1, 5, PageList.NO_PAGE, 6);
        testPageList(5, 1, 5, PageList.NO_PAGE, 6);
        testPageList(6, 6, 8, 5, PageList.NO_PAGE);
        testPageList(8, 6, 8, 5, PageList.NO_PAGE);
    }

    private void testPageList(int currentPage, int expectedStart, int expectedEnd,
                              int expectedEndPageOfPrevBlock, int expectedStartPageOfNextBlock) {
        List<Integer> expectedPageList = getExpectedValue(expectedStart, expectedEnd);
        PageList pageList = questionService.pageListByCurrentPage(currentPage);

        assertThat(pageList.getPages()).isEqualTo(expectedPageList);
        assertThat(pageList.getEndPageOfPrevBlock()).isEqualTo(expectedEndPageOfPrevBlock);
        assertThat(pageList.getStartPageOfNextBlock()).isEqualTo(expectedStartPageOfNextBlock);
    }

    private List<Integer> getExpectedValue(int start, int end) {
        List<Integer> expectedPageList = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            expectedPageList.add(i);
        }
        return expectedPageList;
    }
}
