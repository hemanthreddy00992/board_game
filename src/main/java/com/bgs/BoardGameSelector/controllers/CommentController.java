package com.bgs.BoardGameSelector.controllers;

import com.bgs.BoardGameSelector.dao.CommentDao;
import com.bgs.BoardGameSelector.dao.UserDao;
import com.bgs.BoardGameSelector.model.Comment;
import com.bgs.BoardGameSelector.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserDao userDao;

    @PostMapping("/game/{gameId}")
    public String gamePage(@PathVariable(name = "gameId") int gameId,
                           @RequestParam(name = "content") String content,
                           Model model)
    {
        User loggedInUser = userDao.findByUsername(getLoggedInUsername());
        if (loggedInUser != null)
        {
            Comment comment = new Comment(gameId, loggedInUser.getId(), content);
            commentDao.save(comment);
            System.out.println("Added comment successfully!");
        }

        return "redirect:" + "/game/" + gameId;

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
            return "";
        User user = userDao.findByUsername(username);
        return user.getAvatar();
    }
}
