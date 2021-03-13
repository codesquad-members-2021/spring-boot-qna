package com.codessquad.qna.service;

import com.codessquad.qna.entity.Comment;
import com.codessquad.qna.entity.User;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.repository.CommentRepostiory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepostiory commentRepostiory;
    private final PostService postService;

    public CommentService(CommentRepostiory commentRepostiory, PostService postService) {
        this.commentRepostiory = commentRepostiory;
        this.postService = postService;
    }

    public void addComment(Long postId, User user, String body) {
        Comment comment = new Comment(postService.getPost(postId), user, body);
        commentRepostiory.save(comment);
    }

    public Comment findComment(Long commentId) {
        return getCommentIfExist(commentId);
    }

    public void updateComment(Comment comment, String body) {
        comment.update(body, LocalDateTime.now());
        commentRepostiory.save(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, User user) throws IllegalAccessException {
        Comment comment = getCommentIfExist(commentId);
        if (!comment.isMatchedUser(user)) {
            throw new IllegalAccessException("작성자만 답변을 삭제할 수 있습니다.");
        }
        commentRepostiory.deleteById(commentId);
    }

    private Comment getCommentIfExist(Long commentId) {
        Optional<Comment> comment = commentRepostiory.findById(commentId);
        if (!comment.isPresent()) {
            throw new NotFoundException("해당 댓글을 찾을 수 없습니다.");
        }
        return comment.get();
    }

}
