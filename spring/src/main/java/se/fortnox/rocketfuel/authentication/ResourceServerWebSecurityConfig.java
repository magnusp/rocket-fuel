package se.fortnox.rocketfuel.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.savedrequest.NoOpServerRequestCache;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import reactor.core.publisher.Mono;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity // Enables use of @PreAuthorize
public class ResourceServerWebSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() throws Exception {
        // ensure the passwords are encoded properly
        UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("user").password("password").roles("USER").build());
        manager.createUser(users.username("admin").password("password").roles("USER","ADMIN").build());
        return manager;
    }

    //@Bean
    ReactiveJwtDecoder jwtDecoder() {
        NimbusReactiveJwtDecoder jwtDecoder = (NimbusReactiveJwtDecoder)
            ReactiveJwtDecoders.fromIssuerLocation("https://accounts.google.com");

        List<OAuth2TokenValidator<Jwt>> validators = new ArrayList<>();
        validators.add(new JwtTimestampValidator());
        //validators.add(new JwtIssuerValidator(issuer));

        /*OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator();
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuerUri);
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);*/

        jwtDecoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(validators));

        return jwtDecoder;
    }

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
            .securityMatcher(ServerWebExchangeMatchers.pathMatchers("/api/**"))
            .requestCache().requestCache(NoOpServerRequestCache.getInstance())
            .and()
            .httpBasic().disable()
            .formLogin().disable()
            .csrf().disable()
            .logout().disable()
            .oauth2ResourceServer(oAuth2ResourceServerSpec -> {
                oAuth2ResourceServerSpec
                    .jwt(jwtSpec -> {
                        byte[] sharedSecret = "a really long secret quzbar a really long secret quzbar a really long secret quzbar".getBytes(StandardCharsets.UTF_8);
                        SecretKeySpec secretKey = new SecretKeySpec(sharedSecret, "HmacSHA256");
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
            .anyExchange()
            .authenticated()
            .and()
            .build();
    }

}
