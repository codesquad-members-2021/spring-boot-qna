package com.codessquad.qna.web.service;

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
    @DisplayName("현재 페이지가 0번이면 0부터 4까지 나와야 합니다")
    void testWhenCurrentPageIsZero() {
        long currentPage = 0;
        List<Long> expectedPageList = new ArrayList<>();
        for (long i = 0; i < 5; i++) {
            expectedPageList.add(i);
        }
        List<Long> pageList = questionService.pageList(currentPage);
        assertThat(pageList).isEqualTo(expectedPageList);
    }

    @Test
    @DisplayName("현재 페이지가 3번이면 0부터 4까지 나와야 합니다")
    void testWhenCurrentPageIsThree() {
        long currentPage = 3;
        List<Long> expectedPageList = new ArrayList<>();
        for (long i = 0; i < 5; i++) {
            expectedPageList.add(i);
        }
        List<Long> pageList = questionService.pageList(currentPage);
        assertThat(pageList).isEqualTo(expectedPageList);
    }

    @Test
    @DisplayName("현재 페이지가 4번이면 0부터 4까지 나와야 합니다")
    void testWhenCurrentPageIsFour() {
        long currentPage = 3;
        List<Long> expectedPageList = new ArrayList<>();
        for (long i = 0; i < 5; i++) {
            expectedPageList.add(i);
        }
        List<Long> pageList = questionService.pageList(currentPage);
        assertThat(pageList).isEqualTo(expectedPageList);
    }

    @Test
    @DisplayName("현재 페이지가 5번이면 5부터 9까지 나와야 합니다")
    void testWhenCurrentPageIsFive() {
        long currentPage = 5;
        List<Long> expectedPageList = new ArrayList<>();
        for (long i = 5; i < 10; i++) {
            expectedPageList.add(i);
        }
        List<Long> pageList = questionService.pageList(currentPage);
        assertThat(pageList).isEqualTo(expectedPageList);
    }

    @Test
    @DisplayName("현재 페이지가 11번이면 10부터 11까지 나와야 합니다")
    void testWhenCurrentPageIsEleven() {
        long currentPage = 11;
        List<Long> expectedPageList = new ArrayList<>();
        for (long i = 10; i <= 11; i++) {
            expectedPageList.add(i);
        }
        List<Long> pageList = questionService.pageList(currentPage);
        assertThat(pageList).isEqualTo(expectedPageList);
    }

}
