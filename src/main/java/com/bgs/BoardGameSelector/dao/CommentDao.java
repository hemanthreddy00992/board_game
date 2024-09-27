package com.bgs.BoardGameSelector.dao;

import com.bgs.BoardGameSelector.model.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CommentDao extends CrudRepository<Comment, Long> {
    List<Comment> findByGameId(Integer id);
}
