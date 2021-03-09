package com.codessquad.qna.service;

import com.codessquad.qna.entity.Comment;
import com.codessquad.qna.repository.CommentRepostiory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    CommentRepostiory commentRepostiory;

    public void addComment(Comment comment) {
        commentRepostiory.save(comment);
    }

}
