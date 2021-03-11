package com.codessquad.qna.service;

import com.codessquad.qna.entity.Post;
import com.codessquad.qna.entity.User;
import com.codessquad.qna.exception.CanNotFindPostException;
import com.codessquad.qna.repository.PostRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void addPost(Post post) {
        postRepository.save(post);
    }

    public Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(CanNotFindPostException::new);
    }

    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    public void updatePost(Post oldPost, Post updatePost) {
        postRepository.update(oldPost, updatePost);
    }

    public void deletePost(Post post) {
        postRepository.delete(post);
    }

}
