package com.bgs.BoardGameSelector.model;

import javax.persistence.*;

@Entity
@Table(name = "game_mechanics")
public class GameMechanic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "game_id")
    private Integer gameId;

    @Column(name = "mechanic_id")
    private Integer mechanicId;

    public GameMechanic(Integer gameId, Integer mechanicId) {
        this.gameId = gameId;
        this.mechanicId = mechanicId;
    }

    public GameMechanic() {

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

    public Integer getMechanicId() {
        return mechanicId;
    }

    public void setMechanicId(Integer mechanicId) {
        this.mechanicId = mechanicId;
    }
}
