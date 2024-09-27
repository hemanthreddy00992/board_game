package com.bgs.BoardGameSelector.dao;

import com.bgs.BoardGameSelector.model.GameMechanic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface GameMechanicDao extends CrudRepository<GameMechanic, Integer> {
    public List<GameMechanic> deleteByGameId(Integer id);
}
