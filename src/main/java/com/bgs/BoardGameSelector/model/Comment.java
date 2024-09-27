package com.bgs.BoardGameSelector.model;


import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comment")
public class Comment {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "game_id")
    private int gameId;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "content")
    private String content;

    @Column(name = "reply_to")
    private long replyTo;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created")
    private Date date;

    public Comment() {

    }

    public Comment(int gameId, long userId, String content) {
        this.gameId = gameId;
        this.userId = userId;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(long replyTo) {
        this.replyTo = replyTo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
