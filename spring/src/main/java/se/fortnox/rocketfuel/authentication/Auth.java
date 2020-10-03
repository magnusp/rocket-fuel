package se.fortnox.rocketfuel.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;


public class Auth implements Authentication {
    private long id;
    private final Authentication proxyAuthentication;

    public Auth(JwtAuthenticationToken token) {
        this.id = token.getToken().getClaim("id");
        this.proxyAuthentication = token;
    }

    public long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return proxyAuthentication.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return proxyAuthentication.getCredentials();
    }

    @Override
    public Object getDetails() {
        return proxyAuthentication.getDetails();
    }

    @Override
    public Object getPrincipal() {
        return proxyAuthentication.getPrincipal();
    }

    @Override
    public boolean isAuthenticated() {
        return proxyAuthentication.isAuthenticated();
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        proxyAuthentication.setAuthenticated(isAuthenticated);
    }

    @Override
    public String getName() {
        return proxyAuthentication.getName();
    }
}
