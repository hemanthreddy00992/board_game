package com.bgs.BoardGameSelector.model;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "deletehash")
    private String deletehash;


    public User(String username, String password) {
        this.username = username;
        this.password = password;

        final String defaultAvatar = "https://i.imgur.com/qCkhJd6.jpg";
        final String defaultDeleteHash = "OyffnmIWkxJJZg9";
        this.avatar = defaultAvatar;
        this.deletehash = defaultDeleteHash;
    }

    public User() {
        final String defaultAvatar = "https://i.imgur.com/qCkhJd6.jpg";
        final String defaultDeleteHash = "OyffnmIWkxJJZg9";
        this.avatar = defaultAvatar;
        this.deletehash = defaultDeleteHash;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDeletehash() { return deletehash; }

    public void setDeletehash(String deletehash) { this.deletehash = deletehash; }
}
