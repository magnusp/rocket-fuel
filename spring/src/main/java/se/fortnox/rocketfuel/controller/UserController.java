package se.fortnox.rocketfuel.controller;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import se.fortnox.rocketfuel.api.User;
import se.fortnox.rocketfuel.api.UserResource;

import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Date;

@RestController
public class UserController implements UserResource {

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
    public Mono<User> signIn(ServerWebExchange exchange) {

        return ReactiveSecurityContextHolder
            .getContext()
            .map(securityContext -> {
                JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) securityContext.getAuthentication();
                Jwt jwt = (Jwt) jwtAuthenticationToken.getPrincipal();

                String name = jwt.getClaimAsString("name");
                String email = jwt.getClaimAsString("email");
                Long useridFromRocketFuel = 1L; // From created or existing user in db
                String picture = jwt.getClaimAsString("picture");


                String applicationToken;
                try {
                    applicationToken = buildApplicationToken(name, email, useridFromRocketFuel, picture);
                } catch (JOSEException e) {
                    throw new RuntimeException(e);
                }

                User user = new User();
                user.setId(useridFromRocketFuel);
                user.setName(name);
                user.setEmail(email);
                user.setPicture(picture);
                user.setCoins(0);

                ResponseCookie responseCookie = ResponseCookie.from("applicationToken", applicationToken).build();
                exchange.getResponse().beforeCommit(() -> {
                    exchange.getResponse().addCookie(responseCookie);
                    return Mono.empty();
                });

                return user;
            });
    }

    @Override
    public Mono<Long> signOut() {
        return Mono.error(new UnsupportedOperationException());
    }

    private String buildApplicationToken(String name, String email, Long useridFromRocketFuel, String picture) throws JOSEException {
        final String ISSUER = "rocket-fuel";

        // Generate random 256-bit (32-byte) shared secret
        byte[] sharedSecret = "a really long secret quzbar a really long secret quzbar a really long secret quzbar".getBytes(StandardCharsets.UTF_8);

        // Create HMAC signer
        JWSSigner signer = new MACSigner(sharedSecret);

        // Prepare JWT with claims set
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
            .claim("name", name)
            .claim("email", email)
            .claim("picture", picture)
            .claim("id", useridFromRocketFuel)
            .issuer(ISSUER)
            .expirationTime(new Date(new Date().getTime() + 60 * 1000))
            .build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

        // Apply the HMAC protection
        signedJWT.sign(signer);

        // Serialize to compact form, produces something like
        // eyJhbGciOiJIUzI1NiJ9.SGVsbG8sIHdvcmxkIQ.onO9Ihudz3WkiauDO2Uhyuz0Y18UASXlSc1eS0NkWyA
        return signedJWT.serialize();
    }
}
