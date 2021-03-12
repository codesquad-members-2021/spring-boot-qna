package com.codessquad.qna.service;

import com.codessquad.qna.dto.PostDto;
import com.codessquad.qna.entity.Post;
import com.codessquad.qna.entity.User;
import com.codessquad.qna.exception.CanNotFindPostException;
import com.codessquad.qna.repository.CommentRepostiory;
import com.codessquad.qna.repository.PostRepository;
import com.codessquad.qna.util.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepostiory commentRepostiory;

    public PostService(PostRepository postRepository, CommentRepostiory commentRepostiory) {
        this.postRepository = postRepository;
        this.commentRepostiory = commentRepostiory;
    }

    @Transactional
    public void addPost(Post post) {
        postRepository.save(post);
    }

    //Overload addPost
    @Transactional
    public void addPost(PostDto postDto) {
        postRepository.save(Mapper.mapToPost(postDto));
    }

    public Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(CanNotFindPostException::new);
    }

    public List<Post> getPosts() {
        return (List<Post>) postRepository.findAll();
    }

    @Transactional
    public void updatePost(Long id, PostDto postDto) {
        Post oldPost = getPost(id);
        oldPost.change(Mapper.mapToPost(postDto));
        postRepository.save(oldPost);
    }

    @Transactional
    public void deletePost(Post post, User sessionUser) throws IllegalAccessException {
        if(!post.isMatchedAuthor(sessionUser)){
            throw new IllegalAccessException("다른 사람의 글을 삭제할 수 없습니다");
        }
        postRepository.delete(post);
    }

}
