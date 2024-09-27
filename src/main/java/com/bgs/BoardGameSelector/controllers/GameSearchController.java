package com.bgs.BoardGameSelector.controllers;

import com.bgs.BoardGameSelector.dao.GameSearchDao;
import com.bgs.BoardGameSelector.model.Game;
import com.bgs.BoardGameSelector.services.GameSearchService;
import com.bgs.BoardGameSelector.services.GameSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class GameSearchController {
    @Autowired
    private GameSearchDao gameSearchDao;

    @GetMapping("/query")
    public String runSearch( @RequestParam(name="rankMin", required=false, defaultValue="0") int minRank,
                            @RequestParam(name="rankMax", required = false, defaultValue = "10000") int maxRank,
                            @RequestParam(name="playersMin", required = false, defaultValue = "1") int minNumOfPlayers,
                            @RequestParam(name="playersMax", required = false, defaultValue = "500") int maxNumOfPlayers,
                            @RequestParam(name="yearMin", required = false, defaultValue = "-2000") int minYearPublished,
                            @RequestParam(name="yearMax", required = false, defaultValue = "2019") int maxYearPublished,
                            @RequestParam(name="avgPlayTimeMin", required = false, defaultValue = "0") int minAvgPlayTime,
                            @RequestParam(name="avgPlayTimeMax", required = false, defaultValue = "99999") int maxAvgPlayTime,
                            @RequestParam(name="minPlayTimeMin", required = false, defaultValue = "0") int minMinPlayTime,
                            @RequestParam(name="minPlayTimeMax", required = false, defaultValue = "99999") int maxMinPlayTime,
                            @RequestParam(name="maxPlayTimeMin", required = false, defaultValue = "0") int minMaxPlayTime,
                            @RequestParam(name="maxPlayTimeMax", required = false, defaultValue = "99999") int maxMaxPlayTime,
                            @RequestParam(name="votesMin", required = false, defaultValue = "0") int minVotes,
                            @RequestParam(name="votesMax", required = false, defaultValue = "999999") int maxVotes,
                            @RequestParam(name="ageMin", required = false, defaultValue = "0") int minAge,
                            @RequestParam(name="ageMax", required = false, defaultValue = "999") int maxAge,
                            @RequestParam(name="fansMin", required = false, defaultValue = "0") int minFans,
                            @RequestParam(name="fansMax", required = false, defaultValue = "999999") int maxFans,
                            @RequestParam(name="mechanic", required = false, defaultValue = "") String mechanic,
                            @RequestParam(name="category", required = false, defaultValue = "") String category,
                            @RequestParam(name="page", required = false, defaultValue = "1") int page,
                            @RequestParam(name="sort", required=false, defaultValue="gameRank") String sortBy,
                             Model model) {

        // Instantiate search criteria
        GameSearchService gameSearchService = new GameSearchService(minRank, maxRank, minNumOfPlayers, maxNumOfPlayers,
        minYearPublished, maxYearPublished, minAvgPlayTime, maxAvgPlayTime, minMinPlayTime,
        maxMinPlayTime,  minMaxPlayTime,  maxMaxPlayTime,  minVotes,  maxVotes,
         minAge, maxAge, minFans, maxFans);

        // Generate list of Games from filters (excluding category/mechanic)
        GameSpecificationService spec = new GameSpecificationService(gameSearchService);
        List<Game> foundGames = gameSearchDao.findAll(spec);

        // Convert category/mechanic to integer set
        Set<Integer> cats = convertStringToSet(category);
        Set<Integer> mech = convertStringToSet(mechanic);

        List<Game> result = new ArrayList<Game>();

        /* categories, but no mechanics */
        if ( (cats != null && !cats.isEmpty() ) && (mech == null || mech.isEmpty() ) )
        {
            // Get list of games with matching category tags
            List<Game> gamesByCats = gameSearchDao.joinGameWithCategory(cats);
            // result = (foundGames) ∩ (gamesByCats)
            result = findGameOverlap(foundGames, gamesByCats);
        }

        /* mechanics, but no categories */
        else if ( (cats == null || cats.isEmpty() ) && (mech != null && !mech.isEmpty() ) )
        {
            // Get list of games with matching mechanic tags
            List<Game> gamesByMech = gameSearchDao.joinGameWithMechanic(mech);
            // result = (foundGames) ∩ (gamesByCats)
            result = findGameOverlap(foundGames, gamesByMech);
        }

        /* categories and mechanics ... most specific*/
        else if ((cats != null && !cats.isEmpty() ) && (mech != null && !mech.isEmpty() ))
        {
            // result = (foundGames) ∩ [(gamesByCats) ∩ (gamesByMech)]
            List<Game> gamesByCats = gameSearchDao.joinGameWithCategory(cats);
            List<Game> gamesByMech = gameSearchDao.joinGameWithMechanic(mech);
            List<Game> intersectFoundCat = findGameOverlap(foundGames, gamesByCats);
            result = findGameOverlap(intersectFoundCat, gamesByMech);
        }
        else
        {
            result = foundGames;
        }


        // Sort game list
        if(sortBy != null)
            sortGameList(result, sortBy);

        // Check validity of page param
        int maxNumOfPages = (result.size() / 20) + 1;
        if (page > maxNumOfPages)
            page = maxNumOfPages;

        // Get slice of result list to display
        int startIndex = (page - 1) * 20;
        int endIndex = ((page - 1) * 20) + 20;
        if (endIndex > result.size())
            endIndex = result.size();
        List<Game> slicedResult = result.subList(startIndex, endIndex);

        // Add results to search result page
        model.addAttribute("games", slicedResult);

        // Add num of pages to result page
        model.addAttribute("numOfPages", maxNumOfPages);

        // Debug purposes
        System.out.println(result.size());
        for (Game game : result) {
            System.out.println(game.getName());
            System.out.println(game.getGameRank());
        }

        return "result";
    }

    private void sortGameList(List<Game> games, String sortBy) {
        switch(sortBy) {
            case "gameRank":
                games.sort(new Comparator<Game>() {
                    @Override
                    public int compare(Game g1, Game g2) {
                        return g1.getGameRank().compareTo(g2.getGameRank());
                    }
                });
                break;

            case "name":
                games.sort(new Comparator<Game>() {
                    @Override
                    public int compare(Game g1, Game g2) {
                        return g1.getName().compareTo(g2.getName());
                    }
                });
                break;

            case "votes":
                games.sort(new Comparator<Game>() {
                    @Override
                    public int compare(Game g1, Game g2) {
                        return g2.getNum_votes().compareTo(g1.getNum_votes());
                    }
                });
                break;
        }
    }

    private Set<Integer> convertStringToSet(String s)
    {
        if(s != null && !s.isEmpty())
        {
            // Create list of Strings
            List<String> strValues = Arrays.asList(s.split(","));
            // Convert List<String> -> List<Integer>
            List<Integer> intValues = strValues.stream().map(Integer::parseInt).collect(Collectors.toList());
            // Convert List<Integer> -> Set<Integer>
            Set<Integer> result = new HashSet<Integer>(intValues);
            return result;
        }
        else
            return new HashSet<Integer>();
    }

    private List<Game> findGameOverlap(List<Game> list1, List<Game> list2)
    {
        return  list1.stream()
                .filter(list2::contains)
                .collect(Collectors.toList());
    }
}
