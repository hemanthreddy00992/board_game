package com.bgs.BoardGameSelector.controllers;

import com.bgs.BoardGameSelector.dao.CommentDao;
import com.bgs.BoardGameSelector.dao.GameDao;
import com.bgs.BoardGameSelector.dao.GameSearchDao;
import com.bgs.BoardGameSelector.dao.UserDao;
import com.bgs.BoardGameSelector.model.*;
import com.bgs.BoardGameSelector.services.CommentDisplayService;
import com.bgs.BoardGameSelector.services.FilterSliderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Filter;

@Controller
public class PageController {

    @Autowired
    private GameDao gameDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private GameSearchDao gameSearchDao;

    @GetMapping("/search")
    public String search(Model model) {
        // Initialize slider structs
        List<FilterSliderService> sliders = Arrays.asList(
                new FilterSliderService("rank", "Rank", 1, 500),
                new FilterSliderService("players", "# of Players", 1, 25),
                new FilterSliderService("year", "Year", 0, 2019),
                new FilterSliderService("avgPlayTime", "Avg Play Time", 0, 6000),
                new FilterSliderService("minPlayTime", "Min Play Time", 0, 6000),
                new FilterSliderService("maxPlayTime", "Max Play Time", 0, 6000),
                new FilterSliderService("votes", "# of Votes", 0, 100000),
                new FilterSliderService("age", "Recommended Age", 0, 100),
                new FilterSliderService("fans", "# of Fans", 0, 6000)
        );

        model.addAttribute("sliders", sliders);
        return "search";
    }

    @GetMapping("/")
    public String home(Model model) { return "index"; }

    @GetMapping("/index")
    public String index(Model model) { return "index"; }

    @GetMapping("/game/{gameId}")
    public String gamePage(@PathVariable(name = "gameId") int id, Model model)
    {
        // Fetch game from DB
        Game game = gameDao.findByGameId(id);
        // Fetch game categories and mechanics as a string
        List<String> catList = gameSearchDao.findGameCategoryById(game.getGameId());
        List<String> mechList = gameSearchDao.findGameMechanicById(game.getGameId());
        String cats = String.join(", ", catList);
        String mech = String.join(", ", mechList);
        // Fetch comments on this game along with their creator's username
        ArrayList<Object[]> commentedUsers = userDao.joinUsersWithComment(id);
        ArrayList<CommentDisplayService> comments = new ArrayList<>();
        // Create comment display objects from joined list
        if (commentedUsers.isEmpty())
            comments = null;
        else {
            for (Object[] cd : commentedUsers) {
                CommentDisplayService newComment = new CommentDisplayService(
                        (Integer) cd[0], (Integer) cd[1], (String) cd[2], (Integer) cd[3],
                        (String) cd[4], (String) cd[5], (Date) cd[6]);
                comments.add(newComment);
            }
        }

        // Add objects to model
        model.addAttribute("game", game);
        model.addAttribute("categories", cats);
        model.addAttribute("mechanics", mech);
        model.addAttribute("comments", comments);
        model.addAttribute("username", getLoggedInUsername());
        model.addAttribute("avatar", getLoggedInAvatar());

        return "game";
    }

    @GetMapping("/add")
    public String addGame(Model model) { return "add"; }

    @GetMapping("/edit/{gameId}")
    public String editPage(@PathVariable(name = "gameId") int id, Model model)
    {
        // Fetch game from DB
        Game game = gameDao.findByGameId(id);

        // Get categories
        List<Integer> cats = gameSearchDao.findGameCategoryIdByGameId(id);
        List<Integer> mechs = gameSearchDao.findGameMechanicIdByGameId(id);

        // Add objects to model
        model.addAttribute("game", game);
        model.addAttribute("gameId", id);
        model.addAttribute("categories", cats);
        model.addAttribute("mechanics", mechs);
        model.addAttribute("username", getLoggedInUsername());

        return "edit";
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(name = "error", required = false) String error, Model model) {
        if (error != null)
            model.addAttribute("error", error);

        return "login";
    }

    @GetMapping("/logout-success")
    public String logoutPage(Model model) {return "success"; }

    @GetMapping("/new-user")
    public String newUserPage(Model model) {
        return "new-user";
    }

    @GetMapping("/account")
    public String accountRedirect(Model model) {
        // Get logged-in userId
        User user = userDao.findByUsername(getLoggedInUsername());
        if (user!= null)
        {
            long userId = user.getId();
            return "redirect:" + "/account/" + userId;
        }
        else
            return "redirect:" + "/login";

    }

    @GetMapping("/account/{userId}")
    public String userPage(@PathVariable(name = "userId", required = true) long id, Model model) {
        // Add user to model
        User user = userDao.findById(id).get();
        model.addAttribute("user", user);
        // Add user's games to model
        List<Game> games = gameDao.findByAuthorUsername(user.getUsername());
        model.addAttribute("games", games);

        // Check if logged-in user is authorized for this page
        User attemptedUser = userDao.findByUsername(getLoggedInUsername());
        if (attemptedUser == null || user == null)
        {
            model.addAttribute("incorrectUser", true);
            return "account";
        }

        if (attemptedUser.getId() != user.getId())
            model.addAttribute("incorrectUser", true);
        else
            model.addAttribute("incorrectUser", false);

        return "account";
    }

    private String getLoggedInUsername() {
        // Get username of logged-in user
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();

        } else {
            username = principal.toString();
        }

        return username;
    }

    private String getLoggedInAvatar() {
        String username = getLoggedInUsername();
        if (username.equals("anonymousUser"))
            return null;
        User user = userDao.findByUsername(username);
        return user.getAvatar();
    }

}
