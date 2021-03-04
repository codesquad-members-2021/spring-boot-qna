package com.codessquad.qna.service;

import com.codessquad.qna.entity.Post;
import com.codessquad.qna.exception.CanNotFindPostException;
import com.codessquad.qna.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void addPost(Post post) {
        postRepository.save(post);
    }

    public Post getPost(Long id) {
        final Optional<Post> post = postRepository.findById(id);
        if(!post.isPresent()){
            throw new CanNotFindPostException();
        }
        return post.get();
    }

    public List<Post> getPosts() {
        return postRepository.findAll();
    }

}
