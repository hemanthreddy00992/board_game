package com.bgs.BoardGameSelector.controllers;

import com.bgs.BoardGameSelector.dao.GameCategoryDao;
import com.bgs.BoardGameSelector.dao.GameMechanicDao;
import com.bgs.BoardGameSelector.model.Game;
import com.bgs.BoardGameSelector.dao.GameDao;
import com.bgs.BoardGameSelector.model.GameCategory;
import com.bgs.BoardGameSelector.model.GameMechanic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
public class GameController {

    @Autowired
    private GameDao gameDao;

    @Autowired
    private GameCategoryDao gameCategoryDao;

    @Autowired
    private GameMechanicDao gameMechanicDao;

    @PostMapping("/game/success")
    public String addGame( @RequestParam(name="name", required=false, defaultValue="0") String name,
                           @RequestParam(name="designer", required=false, defaultValue="0") String designer,
                           @RequestParam(name="publisher", required=false, defaultValue="0") String publisher,
                           @RequestParam(name="year", required=false, defaultValue="0") int year,
                           @RequestParam(name="min-players", required=false, defaultValue="0") int minPlayers,
                           @RequestParam(name="max-players", required=false, defaultValue="0") int maxPlayers,
                           @RequestParam(name="age", required=false, defaultValue="0") int age,
                           @RequestParam(name="avg-play-time", required=false, defaultValue="0") int avgPlayTime,
                           @RequestParam(name="min-play-time", required=false, defaultValue="0") int minPlayTime,
                           @RequestParam(name="max-play-time", required=false, defaultValue="0") int maxPlayTime,
                           @RequestParam(name="desc", required=false, defaultValue="0") String desc,
                           @RequestParam(name="img-url", required=false, defaultValue="0") String imgURL,
                           @RequestParam(name="thumb-url", required=false, defaultValue="0") String thumbURL,
                           @RequestParam(name="bgg-url", required=false, defaultValue="0") String bggURL,
                           @RequestParam(name="category", required=false, defaultValue="") String category,
                           @RequestParam(name="mechanic", required=false, defaultValue="") String mechanic,
                             Model model) {

        // Instantiate new game
        Game game = new Game();
        Integer gameId = generateGameId();
        game.setGameId(gameId);
        game.setName(name);
        game.setDesigner(designer);
        game.setPublisher(publisher);
        game.setYear(year);
        game.setMin_player(minPlayers);
        game.setMax_player(maxPlayers);
        game.setAge(age);
        game.setAvg_play_time(avgPlayTime);
        game.setMin_play_time(minPlayTime);
        game.setMax_play_time(maxPlayTime);
        game.setDescription(desc);
        game.setImg_url(imgURL);
        game.setThumb_url(thumbURL);
        game.setBgg_url(bggURL);

        // Get author username (curreny logged in user)
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();

        } else {
            username = principal.toString();
        }
        game.setAuthorUsername(username);

        gameDao.save(game);
        System.out.println("Added game successfully!");

        // Save game category tags
        int[] categories = Arrays.asList(category.split(","))
                .stream()
                .map(String::trim)
                .mapToInt(Integer::parseInt).toArray();
        for(Integer catId : categories)
        {
            GameCategory newRow = new GameCategory(gameId, catId);
            gameCategoryDao.save(newRow);
        }

        // Save game mechanic tags
        int[] mechanics = Arrays.asList(mechanic.split(","))
                .stream()
                .map(String::trim)
                .mapToInt(Integer::parseInt).toArray();
        for(Integer mechId : mechanics)
        {
            GameMechanic newRow = new GameMechanic(gameId, mechId);
            gameMechanicDao.save(newRow);
        }

        return "success";
    }

    @PutMapping("/game/success")
    public String updateGame( @RequestParam(name="name", required=false, defaultValue="0") String name,
                           @RequestParam(name="designer", required=false, defaultValue="0") String designer,
                           @RequestParam(name="publisher", required=false, defaultValue="0") String publisher,
                           @RequestParam(name="year", required=false, defaultValue="0") int year,
                           @RequestParam(name="min-players", required=false, defaultValue="0") int minPlayers,
                           @RequestParam(name="max-players", required=false, defaultValue="0") int maxPlayers,
                           @RequestParam(name="age", required=false, defaultValue="0") int age,
                           @RequestParam(name="avg-play-time", required=false, defaultValue="0") int avgPlayTime,
                           @RequestParam(name="min-play-time", required=false, defaultValue="0") int minPlayTime,
                           @RequestParam(name="max-play-time", required=false, defaultValue="0") int maxPlayTime,
                           @RequestParam(name="desc", required=false, defaultValue="0") String desc,
                           @RequestParam(name="img-url", required=false, defaultValue="0") String imgURL,
                           @RequestParam(name="thumb-url", required=false, defaultValue="0") String thumbURL,
                           @RequestParam(name="bgg-url", required=false, defaultValue="0") String bggURL,
                           @RequestParam(name="category", required=false, defaultValue="") String category,
                           @RequestParam(name="mechanic", required=false, defaultValue="") String mechanic,
                           @RequestParam(name="game-id", required=true) int gameId,
                           Model model) {

        // Instantiate updated game
        Game game = gameDao.findByGameId(gameId);
        game.setName(name);
        game.setDesigner(designer);
        game.setPublisher(publisher);
        game.setYear(year);
        game.setMin_player(minPlayers);
        game.setMax_player(maxPlayers);
        game.setAge(age);
        game.setAvg_play_time(avgPlayTime);
        game.setMin_play_time(minPlayTime);
        game.setMax_play_time(maxPlayTime);
        game.setDescription(desc);
        game.setImg_url(imgURL);
        game.setThumb_url(thumbURL);
        game.setBgg_url(bggURL);

        gameDao.save(game);
        System.out.println("Updated game successfully!");

        // Delte existing tags
        try {
            gameCategoryDao.deleteByGameId(gameId);
            System.out.println("Deleted categories | " + gameId);
        } catch (org.hibernate.StaleStateException e) {
            System.out.println("No existing categories | " + gameId);
        }

        try {
            gameMechanicDao.deleteByGameId(gameId);
            System.out.println("Deleted mechanics | " + gameId);

        } catch (org.hibernate.StaleStateException e) {
            System.out.println("No existing mechanics | " + gameId);
        }

        // Save game category tags
        try {
            int[] categories = Arrays.asList(category.split(","))
                    .stream()
                    .map(String::trim)
                    .mapToInt(Integer::parseInt).toArray();
            for(Integer catId : categories)
            {
                GameCategory newRow = new GameCategory(gameId, catId);
                gameCategoryDao.save(newRow);
            }
        } catch (NumberFormatException e) {
            System.out.println("No category tags | " + gameId);
        }


        // Save game mechanic tags
        try {
            int[] mechanics = Arrays.asList(mechanic.split(","))
                    .stream()
                    .map(String::trim)
                    .mapToInt(Integer::parseInt).toArray();
            for(Integer mechId : mechanics)
            {
                GameMechanic newRow = new GameMechanic(gameId, mechId);
                gameMechanicDao.save(newRow);
            }
        } catch (NumberFormatException e) {
            System.out.println("No mechanic tags | " + gameId);
        }

        return "success";
    }

    @DeleteMapping("/game/success")
    public String deleteGame(@RequestParam(name = "game-id", required = false) int gameId, Model model) {
        int deleted = gameDao.deleteByGameId(gameId);
        if(deleted == 0)
            System.out.println("Failed to delete game!");
        else
            System.out.println("Successfully deleted game!");

        return "success";
    }

    @GetMapping("/game/success")
    public String editGameSuccess(Model model) {
        return "success";
    }

    private Integer generateGameId() {
        System.out.println(gameDao.findMaxId() + 1);
        return gameDao.findMaxId() + 1;
    }

}