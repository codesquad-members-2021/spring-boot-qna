package com.codessquad.qna.question;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

public class QuestionRequest {
    public final static String QUESTION_PATH = "/questions";

    public static ExtractableResponse<Response> requestCreateQuestion(String title, String contents) {
        Map<String, String> body = new HashMap<>();
        body.put("title", title);
        body.put("contents", contents);
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post(QUESTION_PATH)
                .then().log().all().extract();
    }
}
