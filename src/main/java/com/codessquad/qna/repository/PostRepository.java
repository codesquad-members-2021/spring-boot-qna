package com.codessquad.qna.repository;

import com.codessquad.qna.entity.Post;
import com.codessquad.qna.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PostRepository extends CrudRepository<Post, Long> {

    List<Post> findAll();

}
