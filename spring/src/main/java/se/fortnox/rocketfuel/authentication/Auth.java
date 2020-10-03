package se.fortnox.rocketfuel.authentication;

public class Auth {
    private long id;

    public Auth(long userId) {
        this.id = userId;
    }

    public Long getId() {
        return id;
    }
}
