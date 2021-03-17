package com.codessquad.qna.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface GenericRepository<T, ID> extends CrudRepository<T, ID> {
    @Override
    List<T> findAll();
}
