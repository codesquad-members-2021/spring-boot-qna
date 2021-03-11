package com.codessquad.qna.service;

import com.codessquad.qna.entity.Comment;
import com.codessquad.qna.entity.Post;
import com.codessquad.qna.entity.User;
import com.codessquad.qna.repository.CommentRepostiory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CommentService {

    private final CommentRepostiory commentRepostiory;
    private final PostService postService;

    public CommentService(CommentRepostiory commentRepostiory, PostService postService) {
        this.commentRepostiory = commentRepostiory;
        this.postService = postService;
    }

    public void addComment(Long postId, String userId, String body) {
        Comment comment = new Comment(postService.getPost(postId), userId, body);
        commentRepostiory.save(comment);
    }

}
