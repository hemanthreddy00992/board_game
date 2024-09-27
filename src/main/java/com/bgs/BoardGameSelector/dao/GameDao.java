package com.bgs.BoardGameSelector.dao;

        import com.bgs.BoardGameSelector.model.Game;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.data.repository.CrudRepository;
        import org.springframework.transaction.annotation.Transactional;

        import java.util.List;

@Transactional
public interface GameDao extends CrudRepository<Game, Long> {

        public List<Game> findByGameRank(int game_rank);
        public Game findByGameId(int game_id);

        @Query(value = "SELECT MAX(game_id) FROM game", nativeQuery = true)
        public Integer findMaxId();

        public Integer deleteByGameId(int game_id);

        public List<Game> findByAuthorUsername(String authorUsername);
}
