package com.codessquad.qna.user;

import com.codessquad.qna.AcceptanceTest;
import com.codessquad.qna.user.dto.UserResponse;
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

public class UserAcceptanceTest extends AcceptanceTest {
    private final static String PATH = "/users";

    @Test
    @DisplayName("유저를 생성한다.")
    void createUser() {
        // given
        String userId = "pyro";
        String password = "P@ssw0rd";
        String name = "고정완";
        String email = "pyro@gmail.com";

        // when
        ExtractableResponse<Response> response = requestCreateUser(userId, password, name, email);

        // then
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("기존에 존재하는 userId로 유저를 생성하면 실패한다.")
    void createUserWithDuplication() {
        String userId = "pyro";

        // given
        requestCreateUser(userId, "pw", "na", "em@a.com");

        // when
        ExtractableResponse<Response> response = requestCreateUser(userId, "password", "nick", "email@b.com");

        // then
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("유저 목록을 조회한다.")
    @Test
    void getUsers() {
        // given
        requestCreateUser("userId1", "password1", "name1", "email1");
        requestCreateUser("userId2", "password2", "name2", "email2");

        // when
        ExtractableResponse<Response> actualResponse = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(PATH)
                .then().log().all().extract();

        // then
        assertThat(actualResponse.statusCode())
                .isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("유저를 조회한다.")
    @Test
    void getUser() {
        // given
        requestCreateUser("userId1", "password1", "name1", "email1");
        Long id = requestCreateUser("userId2", "password2", "name2", "email2")
                .as(UserResponse.class)
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

    @DisplayName("존재하지 않는 유저를 조회한다.")
    @Test
    void getUser_null() {
        // when
        ExtractableResponse<Response> actualResponse = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(PATH + "/{id}", 100)
                .then().log().all().extract();

        // then
        assertThat(actualResponse.statusCode())
                .isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    private Map<String, String> createParam(String userId, String password, String name, String email) {
        Map<String, String> param = new HashMap<>();
        param.put("userId", userId);
        param.put("password", password);
        param.put("name", name);
        param.put("email", email);
        return param;
    }

    private ExtractableResponse<Response> requestCreateUser(String userId, String password, String name, String email) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createParam(userId, password, name, email))
                .when().post(PATH)
                .then().log().all().extract();
    }
}
