package com.codessquad.qna.service;

import com.codessquad.qna.entity.Comment;
import com.codessquad.qna.entity.User;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;

    public CommentService(CommentRepository commentRepository, PostService postService) {
        this.commentRepository = commentRepository;
        this.postService = postService;
    }

    public Comment addComment(Long postId, User user, String body) {
        Comment comment = new Comment(postService.getPost(postId), user, body);
        commentRepository.save(comment);
        return comment;
    }

    public Comment findComment(Long commentId) {
        return getCommentIfExist(commentId);
    }

    public void updateComment(Comment comment, String body) {
        comment.update(body, LocalDateTime.now());
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, User user) throws IllegalAccessException {
        Comment comment = getCommentIfExist(commentId);
        if (!comment.isMatchedUser(user)) {
            throw new IllegalAccessException("작성자만 답변을 삭제할 수 있습니다.");
        }
        comment.delete();
        commentRepository.save(comment);
    }

    private Comment getCommentIfExist(Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (!comment.isPresent()) {
            throw new NotFoundException("해당 댓글을 찾을 수 없습니다.");
        }
        return comment.get();
    }

}
