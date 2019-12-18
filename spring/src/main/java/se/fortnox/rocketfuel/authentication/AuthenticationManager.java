package se.fortnox.rocketfuel.authentication;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {


    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = List.of(new SimpleGrantedAuthority("some-role"));
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                "alice",
                null,
            simpleGrantedAuthorities
            );
            return Mono.just(auth);
    }
}
