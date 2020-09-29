package se.fortnox.rocketfuel.dao.entity;

import org.springframework.data.annotation.Id;
import se.fortnox.rocketfuel.api.UserDocument;

import java.io.Serializable;
import java.security.Principal;

public class User implements Principal, Serializable {
    @Id
    Long id;
    String email;
    String name;
    Long coins;
    String picture;

    public User() {
    }

    public User(UserDocument userDocument) {
        this.id = userDocument.getId();
        this.email = userDocument.getEmail();
        this.name = userDocument.getName();
        this.coins = userDocument.getCoins();
        this.picture = userDocument.getPicture();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCoins() {
        return coins;
    }

    public void setCoins(Long coins) {
        this.coins = coins;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
