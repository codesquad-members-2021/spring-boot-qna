package com.codessquad.qna.controller;

import com.codessquad.qna.domain.pagination.Criteria;
import com.codessquad.qna.domain.question.Question;
import com.codessquad.qna.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiQuestionControllerTest {

    Logger logger = LoggerFactory.getLogger(ApiQuestionControllerTest.class);

    @LocalServerPort
    private int port;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void paging() throws Exception {
        String url = "http://localhost:" + port + "/";

        logger.info(String.valueOf(mockMvc.perform(MockMvcRequestBuilders
                .get(url))
                .andReturn()
                .getModelAndView()
                .getModelMap()));
        Criteria cri = new Criteria(1);
        Page<Question> paging = questionService.pagingList(cri);

        assertThat(paging.getNumber()).isEqualTo(0);
        assertThat(paging.getSize()).isEqualTo(15);
        assertThat(paging.getContent().get(0).getTitle()).contains("1번째 글");
        assertThat(paging.getContent().get(14).getTitle()).contains("15번째 글");

        assertThatThrownBy(() -> {
            paging.getContent().get(15);
        }).isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessageContaining("Index: 15, Size: 15");
    }
}
