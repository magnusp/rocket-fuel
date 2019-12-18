package se.fortnox.rocketfuel.api;

import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

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
    public Mono<User> signIn(@NotNull String authorizationToken) {
        return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<Long> signOut() {
        return Mono.error(new UnsupportedOperationException());
    }
}
