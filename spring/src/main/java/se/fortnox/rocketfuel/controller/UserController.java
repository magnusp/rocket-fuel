package se.fortnox.rocketfuel.controller;

import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import se.fortnox.rocketfuel.api.User;
import se.fortnox.rocketfuel.api.UserResource;

import javax.validation.constraints.NotNull;

@RestController
public class UserController implements UserResource {
    @Override
    public Mono<User> getCurrent() {
        return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<Integer> createUser(User user) {
        return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<User> getUserByEmail(@NotNull String email, boolean createIfMissing) {
        return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<User> getUserById(long userId) {
        return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<User> signIn(@NotNull String authorizationToken, User requestAttribute) {

        return ReactiveSecurityContextHolder
            .getContext()
            .map(securityContext -> {
                Authentication authentication = securityContext.getAuthentication();
                return requestAttribute;
            });
    }

    @Override
    public Mono<Long> signOut() {
        ServerHttpSecurity.http().oauth2ResourceServer().jwt()
        return Mono.error(new UnsupportedOperationException());
    }
}
