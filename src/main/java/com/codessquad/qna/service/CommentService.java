package com.codessquad.qna.service;

import com.codessquad.qna.entity.Comment;
import com.codessquad.qna.entity.User;
import com.codessquad.qna.repository.CommentRepostiory;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepostiory commentRepostiory;
    private final PostService postService;

    public CommentService(CommentRepostiory commentRepostiory, PostService postService) {
        this.commentRepostiory = commentRepostiory;
        this.postService = postService;
    }

    public void addComment(Long postId, User user, String body) {
        Comment comment = new Comment(postService.getPost(postId), user.getUserId(), body);
        commentRepostiory.save(comment);
    }

}
