package com.codessquad.qna.repository;

import com.codessquad.qna.entity.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {

    List<Post> findByDeletedFalse();

}
