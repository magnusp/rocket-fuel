package se.fortnox.rocketfuel.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import se.fortnox.rocketfuel.api.User;

@Component
public class SomeWebFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        return ReactiveSecurityContextHolder
            .getContext()
            .flux()
            .concatMap(securityContext -> {
                Authentication authentication = securityContext.getAuthentication();
                exchange.getAttributes().put("foo", new User());
                return chain.filter(exchange);
            })
            .single();


        //return chain.filter(exchange);
    }
}
