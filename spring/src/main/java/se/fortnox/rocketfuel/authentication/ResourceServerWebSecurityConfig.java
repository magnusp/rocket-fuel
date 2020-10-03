package se.fortnox.rocketfuel.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.savedrequest.NoOpServerRequestCache;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import reactor.core.publisher.Mono;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity // Enables use of @PreAuthorize
public class ResourceServerWebSecurityConfig {
    final byte[] sharedSecret = "a really long secret quzbar a really long secret quzbar a really long secret quzbar".getBytes(StandardCharsets.UTF_8);
    final SecretKeySpec secretKey = new SecretKeySpec(sharedSecret, "HmacSHA256");

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

    @Bean
    @Order(2)
    public SecurityWebFilterChain second(ServerHttpSecurity http) {

        return http
            .requestCache().requestCache(NoOpServerRequestCache.getInstance())
            .and()
            .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
            .httpBasic().disable()
            .formLogin().disable()
            .csrf().disable()
            .oauth2ResourceServer(oAuth2ResourceServerSpec -> {
                oAuth2ResourceServerSpec
                    .jwt(jwtSpec -> {
                        var decoder = NimbusReactiveJwtDecoder.withSecretKey(secretKey)
                            .macAlgorithm(MacAlgorithm.HS256)
                            .build();
                        jwtSpec.jwtDecoder(decoder);
                    })
                    .bearerTokenConverter(exchange -> {
                        HttpCookie applicationToken = exchange.getRequest().getCookies().getFirst("applicationToken");
                        if(applicationToken == null) {
                            return Mono.error(new RuntimeException("No applicationToken"));
                        }
                        return Mono.just(new BearerTokenAuthenticationToken(applicationToken.getValue()));
                    });
            })
            .authorizeExchange()
            .pathMatchers("/api/**").authenticated()
            .and().build();
    }

}
