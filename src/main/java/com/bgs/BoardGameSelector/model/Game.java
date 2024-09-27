package com.bgs.BoardGameSelector.model;

import com.bgs.BoardGameSelector.dao.GameDao;
import com.bgs.BoardGameSelector.dao.GameSearchDao;
import com.fasterxml.jackson.annotation.JsonTypeId;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "game")
public class Game {

    @Column(name = "game_rank")
    private Integer gameRank;

    @Column(name = "bgg_url")
    private String bgg_url;

    @Id
    @Column(name = "game_id")
    private Integer gameId;

    @Column(name = "name")
    private String name;

    @Column(name = "min_player")
    private Integer min_player;

    @Column(name = "max_player")
    private Integer max_player;

    @Column(name = "avg_play_time")
    private Integer avg_play_time;

    @Column(name = "min_play_time")
    private  Integer min_play_time;

    @Column(name = "max_play_time")
    private Integer max_play_time;

    @Column(name = "year")
    private Integer year;

    @Column(name = "avg_rating")
    private Double avg_rating;

    @Column(name = "num_votes")
    private Integer num_votes;

    @Column(name = "img_url")
    private String img_url;

    @Column(name = "thumb_url")
    private String thumb_url;

    @Column(name = "age")
    private Integer age;

    @Column(name = "designer")
    private String designer;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "fans")
    private Integer fans;

    @Column(name = "description")
    private String description;

    @Column(name = "is_user_made")
    private Boolean is_user_made;

    @Column(name = "author_username")
    private String authorUsername;

    public Integer getGameRank() {
        return gameRank;
    }

    public String getBgg_url() {
        return bgg_url;
    }

    public Integer getGameId() {
        return gameId;
    }

    public String getName() {
        return name;
    }

    public Integer getMin_player() {
        return min_player;
    }

    public Integer getMax_player() {
        return max_player;
    }

    public Integer getAvg_play_time() {
        return avg_play_time;
    }

    public Integer getMin_play_time() {
        return min_play_time;
    }

    public Integer getMax_play_time() {
        return max_play_time;
    }

    public Integer getYear() {
        return year;
    }

    public Double getAvg_rating() {
        return avg_rating;
    }

    public Integer getNum_votes() {
        return num_votes;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public Integer getAge() {
        return age;
    }

    public String getDesigner() { return designer; }

    public String getPublisher() {
        return publisher;
    }

    public Integer getFans() {
        return fans;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getIs_user_made() {
        return is_user_made;
    }


    public void setGameRank(Integer gameRank) {
        this.gameRank = gameRank;
    }

    public void setBgg_url(String bgg_url) {
        this.bgg_url = bgg_url;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMin_player(Integer min_player) {
        this.min_player = min_player;
    }

    public void setMax_player(Integer max_player) {
        this.max_player = max_player;
    }

    public void setAvg_play_time(Integer avg_play_time) {
        this.avg_play_time = avg_play_time;
    }

    public void setMin_play_time(Integer min_play_time) {
        this.min_play_time = min_play_time;
    }

    public void setMax_play_time(Integer max_play_time) {
        this.max_play_time = max_play_time;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setAvg_rating(Double avg_rating) {
        this.avg_rating = avg_rating;
    }

    public void setNum_votes(Integer num_votes) {
        this.num_votes = num_votes;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setDesigner(String designer) { this.designer = designer; }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setFans(Integer fans) {
        this.fans = fans;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIs_user_made(Boolean is_user_made) {
        this.is_user_made = is_user_made;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }



    public Game(Integer gameRank, String bggURL, Integer gameId, String name, Integer minPlayers, Integer maxPlayers,
                Integer avgPlayTime, Integer minPlayTime, Integer maxPlayTime, Integer year, Double avgRating,
                Integer numOfVotes, String imageURL, String thumbnailURL, Integer age,
                String designer, String publisher, Integer numOfFans, String description, Boolean isUserAdded)
    {
        this.gameRank = gameRank;
        this.bgg_url = bggURL;
        this.gameId = gameId;
        this.name = name;
        this.min_player = minPlayers;
        this.max_player = maxPlayers;
        this.avg_play_time = avgPlayTime;
        this.min_play_time = minPlayTime;
        this.max_play_time = maxPlayTime;
        this.year = year;
        this.avg_rating = avgRating;
        this.num_votes = numOfVotes;
        this.img_url = imageURL;
        this.thumb_url = thumbnailURL;
        this.age = age;
        this.designer = designer;
        this.publisher = publisher;
        this.fans = numOfFans;
        this.description = description;
        this.is_user_made = isUserAdded;
    }

    public Game() {

        this.gameRank = 5001;
        this.num_votes = 0;
        this.fans = 0;
        this.is_user_made = true;
    }


}