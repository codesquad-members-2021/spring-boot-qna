package com.codessquad.qna.question;

import com.codessquad.qna.AcceptanceTest;
import com.codessquad.qna.question.dto.QuestionResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionAcceptanceTest extends AcceptanceTest {
    private final static String PATH = "/questions";

    @Test
    @DisplayName("질문을 생성한다.")
    void createQuestion() {
        // given
        String writer = "pyro";
        String title = "ATDD is so difficult";
        String contents = "Lorem Ipsum";

        // when
        ExtractableResponse<Response> response = requestCreateQuestion(writer, title, contents);

        // then
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("질문 목록을 조회한다.")
    @Test
    void getQuestions() {
        // given
        requestCreateQuestion("writer1", "title1", "contents1");
        requestCreateQuestion("writer2", "title2", "contents2");

        // when
        ExtractableResponse<Response> actualResponse = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(PATH)
                .then().log().all().extract();

        // then
        assertThat(actualResponse.statusCode())
                .isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("질문을 조회한다.")
    @Test
    void getQuestion() {
        // given
        requestCreateQuestion("writer1", "title1", "contents1");
        Long id = requestCreateQuestion("writer2", "title2", "contents2")
                .as(QuestionResponse.class)
                .getId();

        // when
        ExtractableResponse<Response> actualResponse = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(PATH + "/{id}", id)
                .then().log().all().extract();

        // then
        assertThat(actualResponse.statusCode())
                .isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("존재하지 않는 질문을 조회한다.")
    @Test
    void getQuestion_null() {
        // when
        ExtractableResponse<Response> actualResponse = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(PATH + "/{id}", 100)
                .then().log().all().extract();

        // then
        assertThat(actualResponse.statusCode())
                .isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    private Map<String, String> createParam(String writer, String title, String contents) {
        Map<String, String> param = new HashMap<>();
        param.put("writer", writer);
        param.put("title", title);
        param.put("contents", contents);
        return param;
    }

    private ExtractableResponse<Response> requestCreateQuestion(String writer, String title, String contents) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createParam(writer, title, contents))
                .when().post(PATH)
                .then().log().all().extract();
    }
}
