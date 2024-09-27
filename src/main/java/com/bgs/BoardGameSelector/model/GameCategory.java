package com.bgs.BoardGameSelector.model;

import javax.persistence.*;

@Entity
@Table(name = "game_categories")
public class GameCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "game_id")
    private Integer gameId;

    @Column(name = "category_id")
    private Integer categoryId;

    public GameCategory(Integer gameId, Integer categoryId) {
        this.gameId = gameId;
        this.categoryId = categoryId;
    }

    public GameCategory() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
