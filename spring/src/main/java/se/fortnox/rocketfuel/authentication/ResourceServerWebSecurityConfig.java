package se.fortnox.rocketfuel.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.savedrequest.NoOpServerRequestCache;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import reactor.core.publisher.Mono;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity // Enables use of @PreAuthorize
public class ResourceServerWebSecurityConfig {

    @Bean
    @Order(1)
    public SecurityWebFilterChain first(ServerHttpSecurity http) {
        return http
            .securityMatcher(ServerWebExchangeMatchers.pathMatchers("/api/users/authenticate"))
            .requestCache().requestCache(NoOpServerRequestCache.getInstance())
            .and()
            .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer
                .jwt(withDefaults())
            )
            .httpBasic().disable()
            .formLogin().disable()
            .csrf().disable()
            .logout().disable()
            .authorizeExchange()
            .pathMatchers(HttpMethod.DELETE,"/api/users/authenticate")
            .permitAll()
            .and()
            .authorizeExchange()
            .anyExchange()
            .authenticated()
            .and()
            .build();
    }

    private AuthenticationWebFilter authenticationWebFilter(ReactiveAuthenticationManager authenticationManager) {
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(authenticationManager);
        authenticationWebFilter.setServerAuthenticationConverter(exchange -> {
            return Mono.fromCallable(() -> {
                HttpCookie applicationTokenCookie = exchange.getRequest().getCookies().getFirst("applicationToken");
                if(applicationTokenCookie == null) {
                    return null;
                }
                return applicationTokenCookie.getValue();
            })
                .map(BearerTokenAuthenticationToken::new);
        });
        return authenticationWebFilter;
    }

    @Bean
    @Order(2)
    public SecurityWebFilterChain second(ServerHttpSecurity http) {
        RocketFuelAuthenticationManager authAuthenticationManager = new RocketFuelAuthenticationManager();

        return http
            .requestCache().requestCache(NoOpServerRequestCache.getInstance())
            .and()
            .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
            .httpBasic().disable()
            .formLogin().disable()
            .csrf().disable()
            .authorizeExchange()
            .pathMatchers("/api/**").authenticated()
            .and().addFilterAt(authenticationWebFilter(authAuthenticationManager), SecurityWebFiltersOrder.AUTHENTICATION)
            .logout()
            .and().build();
    }

}
