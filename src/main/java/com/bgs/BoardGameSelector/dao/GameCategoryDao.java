package com.bgs.BoardGameSelector.dao;

import com.bgs.BoardGameSelector.model.GameCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface GameCategoryDao extends CrudRepository<GameCategory, Integer> {
    public List<GameCategory> deleteByGameId(Integer id);
}
