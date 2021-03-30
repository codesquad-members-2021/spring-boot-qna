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
    @DisplayName("현재 페이지가 1번이면 1부터 5까지 나와야 합니다")
    void testPageListIsOk() {
        testPageList(1, 1, 5);
        testPageList(3, 1, 5);
        testPageList(5, 1, 5);

        testPageList(6, 6, 8);
        testPageList(8, 6, 8);
    }

    private void testPageList(long currentPage, long expectedStart, long expectedEnd) {
        List<Long> expectedPageList = getExpectedValue(expectedStart, expectedEnd);
        PageList pageList = questionService.pageListByCurrentPage(currentPage);
        assertThat(pageList.getPages()).isEqualTo(expectedPageList);
    }

    private List<Long> getExpectedValue(long start, long end) {
        List<Long> expectedPageList = new ArrayList<>();
        for (long i = start; i <= end; i++) {
            expectedPageList.add(i);
        }
        return expectedPageList;
    }
}
