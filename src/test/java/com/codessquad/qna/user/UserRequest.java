package com.codessquad.qna.user;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

public class UserRequest {
    public final static String USER_PATH = "/users";

    public static ExtractableResponse<Response> requestCreateUser(String userId, String password, String name, String email) {
        Map<String, String> body = new HashMap<>();
        body.put("userId", userId);
        body.put("password", password);
        body.put("name", name);
        body.put("email", email);

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post(USER_PATH)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> requestSignIn(String userId, String password) {
        Map<String, String> body = new HashMap<>();
        body.put("userId", userId);
        body.put("password", password);

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post(USER_PATH + "/signin")
                .then().log().all().extract();
    }
}
