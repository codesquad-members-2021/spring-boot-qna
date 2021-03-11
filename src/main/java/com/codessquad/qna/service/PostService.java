package com.codessquad.qna.service;

import com.codessquad.qna.dto.PostDto;
import com.codessquad.qna.entity.Post;
import com.codessquad.qna.exception.CanNotFindPostException;
import com.codessquad.qna.repository.CommentRepostiory;
import com.codessquad.qna.repository.PostRepository;
import com.codessquad.qna.util.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepostiory commentRepostiory;

    public PostService(PostRepository postRepository, CommentRepostiory commentRepostiory) {
        this.postRepository = postRepository;
        this.commentRepostiory = commentRepostiory;
    }

    public void addPost(Post post) {
        postRepository.save(post);
    }

    //Overload addPost
    public void addPost(PostDto postDto) {
        postRepository.save(Mapper.mapToPost(postDto));
    }

    public Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(CanNotFindPostException::new);
    }

    public List<Post> getPosts() {
        return (List<Post>) postRepository.findAll();
    }

    public void updatePost(Long id, PostDto postDto) {
        Post oldPost = getPost(id);
        oldPost.change(Mapper.mapToPost(postDto));
        postRepository.save(oldPost);
    }

    public void deletePost(Post post) {
        commentRepostiory.deleteAllByPost(post);
        postRepository.delete(post);
    }

}
