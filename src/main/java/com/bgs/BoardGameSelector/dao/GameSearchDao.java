package com.bgs.BoardGameSelector.dao;

import com.bgs.BoardGameSelector.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface GameSearchDao extends JpaRepository<Game, Long>, JpaSpecificationExecutor<Game> {
    @Query(
            value = "SELECT game_rank, bgg_url, g.game_id, name, min_player, max_player, avg_play_time, min_play_time, max_play_time, year, avg_rating,\n" +
                    "num_votes, img_url, thumb_url, age, designer, publisher, fans, g.description, is_user_made, author_username\n" +
                    "FROM game g\n" +
                    "INNER JOIN game_categories gc on g.game_id = gc.game_id\n" +
                    "INNER JOIN category c on gc.category_id = c.id\n" +
                    "WHERE c.id IN :targetCats",
    nativeQuery = true)
    List<Game> joinGameWithCategory(@Param("targetCats")Set<Integer> cats);

    @Query(
            value = "SELECT game_rank, bgg_url, g.game_id, name, min_player, max_player, avg_play_time, min_play_time, max_play_time, year, avg_rating,\n" +
                    "num_votes, img_url, thumb_url, age, designer, publisher, fans, g.description, is_user_made, author_username\n" +
                    "FROM game g\n" +
                    "INNER JOIN game_mechanics gm on g.game_id = gm.game_id\n" +
                    "INNER JOIN mechanic m on gm.mechanic_id = m.id\n" +
                    "WHERE m.id IN :targetMech",
            nativeQuery = true)
    List<Game> joinGameWithMechanic(@Param("targetMech")Set<Integer> cats);

    @Query(
            value = "SELECT m.description\n" +
                    "FROM game g\n" +
                    "INNER JOIN game_mechanics gm on g.game_id = gm.game_id\n" +
                    "INNER JOIN mechanic m on gm.mechanic_id = m.id\n" +
                    "WHERE g.game_id = :gameId",
            nativeQuery = true)
    List<String> findGameMechanicById(@Param("gameId")Integer gameId);

    @Query(
            value = "SELECT c.description\n" +
                    "FROM game g\n" +
                    "INNER JOIN game_categories gc on g.game_id = gc.game_id\n" +
                    "INNER JOIN category c on gc.category_id = c.id\n" +
                    "WHERE g.game_id = :gameId",
            nativeQuery = true)
    List<String> findGameCategoryById(@Param("gameId")Integer gameId);

    @Query(
            value = "SELECT c.id\n" +
                    "FROM game g\n" +
                    "INNER JOIN game_categories gc on g.game_id = gc.game_id\n" +
                    "INNER JOIN category c on gc.category_id = c.id\n" +
                    "WHERE g.game_id = :gameId",
            nativeQuery = true)
    List<Integer> findGameCategoryIdByGameId(@Param("gameId")Integer gameId);

    @Query(
            value = "SELECT m.id\n" +
                    "FROM game g\n" +
                    "INNER JOIN game_mechanics gm on g.game_id = gm.game_id\n" +
                    "INNER JOIN mechanic m on gm.mechanic_id = m.id\n" +
                    "WHERE g.game_id = :gameId",
            nativeQuery = true)
    List<Integer> findGameMechanicIdByGameId(@Param("gameId")Integer gameId);
}
