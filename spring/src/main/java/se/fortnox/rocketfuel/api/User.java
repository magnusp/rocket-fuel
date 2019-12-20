 package se.fortnox.rocketfuel.api;

 import com.fasterxml.jackson.annotation.JsonIgnore;

 import java.io.Serializable;
 import java.security.Principal;

 public class User implements Principal, Serializable {

    private Long id;

    private String email;

    private String name;

    @JsonIgnore
    private Integer coins;

    private String picture;

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

    public Integer getCoins() {
        return coins;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPicture() {
        return picture;
    }

}
