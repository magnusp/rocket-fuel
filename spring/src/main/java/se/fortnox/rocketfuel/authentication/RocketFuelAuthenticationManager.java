package se.fortnox.rocketfuel.authentication;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtReactiveAuthenticationManager;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import reactor.core.publisher.Mono;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class RocketFuelAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtReactiveAuthenticationManager proxyAuthenticationManager;
    // TODO: Make configurable
    private final byte[] SHARED_SECRET = "a really long secret quzbar a really long secret quzbar a really long secret quzbar".getBytes(StandardCharsets.UTF_8);
    public RocketFuelAuthenticationManager() {
        Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter
            = new ReactiveJwtAuthenticationConverterAdapter(new JwtAuthenticationConverter());
        SecretKeySpec secretKey = new SecretKeySpec(SHARED_SECRET, "HmacSHA256");
        ReactiveJwtDecoder jwtDecoder = NimbusReactiveJwtDecoder.withSecretKey(secretKey).build();
        proxyAuthenticationManager = new JwtReactiveAuthenticationManager(jwtDecoder);
        proxyAuthenticationManager.setJwtAuthenticationConverter(jwtAuthenticationConverter);
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return proxyAuthenticationManager.authenticate(authentication)
            .cast(JwtAuthenticationToken.class)
            .map(Auth::new);
    }
}
