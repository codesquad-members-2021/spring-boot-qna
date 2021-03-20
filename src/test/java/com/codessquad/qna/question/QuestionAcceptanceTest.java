package com.codessquad.qna.question;

import com.codessquad.qna.AcceptanceTest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.codessquad.qna.question.QuestionRequest.QUESTION_PATH;
import static com.codessquad.qna.question.QuestionRequest.requestCreateQuestion;
import static org.assertj.core.api.Assertions.assertThat;

public class QuestionAcceptanceTest extends AcceptanceTest {
    @DisplayName("질문 목록을 조회한다.")
    @Test
    void getQuestions() {
        // given
        requestCreateQuestion("title1", "contents1");
        requestCreateQuestion("title2", "contents2");

        // when
        ExtractableResponse<Response> actualResponse = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(QUESTION_PATH)
                .then().log().all().extract();

        // then
        assertThat(actualResponse.statusCode())
                .isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("존재하지 않는 질문을 조회하면 실패한다.")
    @Test
    void getQuestionNotExist() {
        // given
        long inValidId = 736;

        // when
        ExtractableResponse<Response> actualResponse = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(QUESTION_PATH + "/{id}", inValidId)
                .then().log().all().extract();

        // then
        assertThat(actualResponse.statusCode())
                .isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
