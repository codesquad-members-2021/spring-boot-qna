package com.codessquad.qna.repository;

import com.codessquad.qna.entity.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {

    @Query("select p from Post p where p.deleteFlag = false")
    List<Post> findAll();

}
