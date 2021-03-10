package com.codessquad.qna.question;

import com.codessquad.qna.AcceptanceTest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;

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
                .isEqualTo(CREATED.value());
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
