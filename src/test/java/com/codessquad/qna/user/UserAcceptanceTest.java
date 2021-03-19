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

import static com.codessquad.qna.user.UserRequest.USER_PATH;
import static com.codessquad.qna.user.UserRequest.requestCreateUser;
import static org.assertj.core.api.Assertions.assertThat;

public class UserAcceptanceTest extends AcceptanceTest {
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
    @DisplayName("비어있는 값으로 유저를 생성하면 실패한다.")
    void createUserWithBlank() {
        // given
        String userId = " ";
        String password = "  ";
        String name = "   ";
        String email = "    ";

        // when
        ExtractableResponse<Response> response = requestCreateUser(userId, password, name, email);

        // then
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("잘못된 이메일로 유저를 생성하면 실패한다.")
    void createUserWithBadEmail() {
        // given
        String userId = "pyro";
        String password = "P@ssw0rd";
        String name = "고정완";
        String email = "이건 이메일이 아니지롱~!";

        // when
        ExtractableResponse<Response> response = requestCreateUser(userId, password, name, email);

        // then
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
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
        requestCreateUser("userId1", "password1", "name1", "email1@a.com");
        requestCreateUser("userId2", "password2", "name2", "email2@a.com");

        // when
        ExtractableResponse<Response> actualResponse = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(USER_PATH)
                .then().log().all().extract();

        // then
        assertThat(actualResponse.statusCode())
                .isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("유저를 조회한다.")
    @Test
    void getUser() {
        // given
        requestCreateUser("userId1", "password1", "name1", "email1@a.com");
        Long id = requestCreateUser("userId2", "password2", "name2", "email2@a.com")
                .as(UserResponse.class)
                .getId();

        // when
        ExtractableResponse<Response> actualResponse = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(USER_PATH + "/{id}", id)
                .then().log().all().extract();

        // then
        assertThat(actualResponse.statusCode())
                .isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("존재하지 않는 유저를 조회하면 실패한다.")
    @Test
    void getUserNotExist() {
        // given
        long inValidId = 736;

        // when
        ExtractableResponse<Response> actualResponse = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(USER_PATH + "/{id}", inValidId)
                .then().log().all().extract();

        // then
        assertThat(actualResponse.statusCode())
                .isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
