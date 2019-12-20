package se.fortnox.rocketfuel.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;
import se.fortnox.rocketfuel.api.User;
import se.fortnox.rocketfuel.api.UserResource;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;

@RestController
public class UserController implements UserResource {
    private static final List<SimpleGrantedAuthority> DEFAULT_GRANTED_AUTHORITIES = List.of(new SimpleGrantedAuthority("User"));

    @Override
    public Mono<User> getCurrent(Principal principal) {
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
    public Mono<User> signIn(WebSession session) {

        return ReactiveSecurityContextHolder
            .getContext()
            .map(securityContext -> {
                JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) securityContext.getAuthentication();
                Jwt jwt = (Jwt) jwtAuthenticationToken.getPrincipal();

                User user = new User();
                user.setName(jwt.getClaimAsString("name"));

                PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken = new PreAuthenticatedAuthenticationToken(
                    user,
                    jwtAuthenticationToken.getToken(),
                    DEFAULT_GRANTED_AUTHORITIES
                );
                SecurityContextImpl securityContext1 = new SecurityContextImpl(preAuthenticatedAuthenticationToken);
                session.getAttributes().putIfAbsent(
                    WebSessionServerSecurityContextRepository.DEFAULT_SPRING_SECURITY_CONTEXT_ATTR_NAME,
                    securityContext1
                );
                Authentication authentication = securityContext.getAuthentication();
                return user;
            });
        //return Mono.just(new User());
    }

    @Override
    public Mono<Long> signOut() {
        return Mono.error(new UnsupportedOperationException());
    }
}
