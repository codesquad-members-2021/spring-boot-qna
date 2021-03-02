package com.codessquad.qna.service;

import com.codessquad.qna.entity.Post;
import com.codessquad.qna.repository.PostRepository;
import com.codessquad.qna.repository.PostRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;


class PostServiceTest {

    PostService postService;

    @BeforeEach
    void rollback() {
        postService = new PostService();
    }

    @Test
    @DisplayName("Post 가 정상적으로 저장되는지 확인한다.")
    void addPost() {
        Post post = new Post("TIL", "roach", "오늘은 스프링을 하고있다.");
        postService.addPost(post);
        assertThat(postService.getPost(1)).isEqualTo(post);
    }

    @Test
    @DisplayName("저장한 Post 를 정상적으로 가져오는지 확인한다.")
    void getPost() {
        Post post = new Post("TIL", "roach", "오늘은 스프링을 하고있다.");
        Post post1 = new Post("TIL2", "roach", "오늘은 스프링을 하고있다. 123");
        postService.addPost(post);
        postService.addPost(post1);
        assertThat(postService.getPost(1)).isEqualTo(post);
    }

    @Test
    void getPosts() {
    }
}
