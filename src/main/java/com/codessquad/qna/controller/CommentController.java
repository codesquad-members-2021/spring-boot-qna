package com.codessquad.qna.controller;

import com.codessquad.qna.entity.Comment;
import com.codessquad.qna.entity.Post;
import com.codessquad.qna.entity.User;
import com.codessquad.qna.service.CommentService;
import com.codessquad.qna.service.PostService;
import com.codessquad.qna.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    private Logger logger = LoggerFactory.getLogger(CommentController.class);
    private final PostService postService;
    private final CommentService commentService;

    public CommentController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    /**
     * 세션에 로그인 되어 있어야만 작성 가능하며 로그인 되어 있지 않을시 NotExistLoggedUserInSession 발생
     * 로그인 되어 있다면 정상적으로 댓글 작성 가능함
     * @param id
     * @param body
     * @param httpSession
     * @return redirect:/questions/%d
     */
    @PostMapping("/questions/{id}/comments")
    public String addCommend(@PathVariable Long id, String body, HttpSession httpSession) {
        Post post = postService.getPost(id);
        User commentAuthor = HttpSessionUtils.getUserFromSession(httpSession);
        Comment comment = new Comment(post, commentAuthor.getUserId(), body);
        commentService.addComment(comment);
        logger.info("add Comment : " + comment.toString());
        return String.format("redirect:/questions/%d", id);
    }
}
