package com.codessquad.qna.service;

import com.codessquad.qna.entity.Post;
import com.codessquad.qna.exception.CanNotFindPostException;
import com.codessquad.qna.repository.PostRepository;
import com.codessquad.qna.repository.PostRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void addPost(Post post) {
        int index = postRepository.size() + 1;
        post.setPostId(index);
        postRepository.save(post);
    }

    public Post getPost(int id) {
        final Optional<Post> post = postRepository.getPost(id - 1);
        return post.get();
    }

    public List<Post> getPosts() {
        return postRepository.getPosts();
    }

}
