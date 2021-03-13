package com.codessquad.qna.service;

import com.codessquad.qna.MvcConfig;
import com.codessquad.qna.entity.Post;
import com.codessquad.qna.entity.User;
import com.codessquad.qna.exception.CanNotFindPostException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


@Rollback(value = true)
class PostServiceTest {

    static ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MvcConfig.class);
    static UserService userService;
    static User user;
    PostService postService;

    @BeforeAll
    static void saveUser() {
        user = new User("roach", "1234", "roach", "123@naver.com");
        userService = applicationContext.getBean("userService", UserService.class);
        userService.save(user);
    }

    @BeforeEach
    void rollback() {
        postService = applicationContext.getBean("postService", PostService.class);
    }

    @Test
    @DisplayName("Post 가 정상적으로 저장되는지 확인한다.")
    void addPost() {
        Post post = new Post("TIL", user, "오늘은 스프링을 하고있다.");
        postService.addPost(post);
        assertThat(postService.getPost(1L).getPostId()).isEqualTo(post.getPostId());
    }

    @Test
    @DisplayName("저장한 Post 를 정상적으로 가져오는지 확인한다.")
    void getPost() {
        Post post = new Post("TIL", user, "오늘은 스프링을 하고있다. 444");
        Post post1 = new Post("TIL2", user, "오늘은 스프링을 하고있다. 123");
        postService.addPost(post);
        postService.addPost(post1);
        assertThat(postService.getPost(1L).getPostId()).isEqualTo(post.getPostId());
    }

    @Test
    @DisplayName("없는 포스트를 가져오려고 할때 CanNotFindPostException 이 발생하는지 확인한다.")
    void failGetPost() {
        assertThatExceptionOfType(CanNotFindPostException.class)
                .isThrownBy(() -> postService.getPost(100L))
                .withMessage("해당 POST 를 찾을 수 없습니다.");
    }

}
