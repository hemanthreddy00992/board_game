package com.bgs.BoardGameSelector.services;

import com.bgs.BoardGameSelector.model.Game;
import com.bgs.BoardGameSelector.services.GameSearchService;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GameSpecificationService implements Specification<Game> {

    private GameSearchService criteria;

    public GameSpecificationService(GameSearchService gs) {
        criteria = gs;
    }

    @Override
    public Predicate toPredicate(Root<Game> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate p = cb.conjunction();

        /* Filter on . . . */

        // Rank
        p.getExpressions().add(cb.between(root.get("gameRank"), criteria.minRank, criteria.maxRank));

        // Number of Players
        p.getExpressions().add(cb.between(root.get("min_player"), criteria.minNumOfPlayers, criteria.maxNumOfPlayers));
        p.getExpressions().add(cb.between(root.get("max_player"), criteria.minNumOfPlayers, criteria.maxNumOfPlayers));

        // Year Published Published
        p.getExpressions().add(cb.between(root.get("year"), criteria.minYearPublished, criteria.maxYearPublished));

        // Average Play Time
        p.getExpressions().add(cb.between(root.get("avg_play_time"), criteria.minAvgPlayTime, criteria.maxAvgPlayTime));

        // Minimum Play Time
        p.getExpressions().add(cb.between(root.get("min_play_time"), criteria.minMinPlayTime, criteria.maxMinPlayTime));

        // Maximum Play Time
        p.getExpressions().add(cb.between(root.get("max_play_time"), criteria.minMaxPlayTime, criteria.maxMaxPlayTime));

        // Number of Votes
        p.getExpressions().add(cb.between(root.get("num_votes"), criteria.minVotes, criteria.maxVotes));

        // Recommended Age
        p.getExpressions().add(cb.between(root.get("age"), criteria.minAge, criteria.maxAge));

        // Number of Fans
        p.getExpressions().add(cb.between(root.get("fans"), criteria.minFans, criteria.maxFans));

        return p;
    }

}