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
import se.fortnox.rocketfuel.api.UserDocument;
import se.fortnox.rocketfuel.api.UserResource;
import se.fortnox.rocketfuel.dao.UserRepository;
import se.fortnox.rocketfuel.dao.entity.User;

import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Date;

@RestController
public class UserController implements UserResource {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<UserDocument> getCurrent(Principal principal) {
        return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<Integer> createUser(UserDocument userDocument) {
        return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<UserDocument> getUserByEmail(@NotNull String email, boolean createIfMissing) {
        return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<UserDocument> getUserById(long userId) {
        return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<UserDocument> signIn(ServerWebExchange exchange) {

        return ReactiveSecurityContextHolder
            .getContext()
            .flatMap(securityContext -> {
                JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) securityContext.getAuthentication();
                Jwt jwt = (Jwt) jwtAuthenticationToken.getPrincipal();
                String email = jwt.getClaimAsString("email");

                return userRepository.findByEmail(email)
                    .switchIfEmpty(Mono.defer(() -> {
                        String name = jwt.getClaimAsString("name");
                        String picture = jwt.getClaimAsString("picture");
                        User user = new User();
                        user.setName(name);
                        user.setPicture(picture);
                        user.setEmail(email);
                        return userRepository.save(user.getEmail(), user.getName(), user.getPicture());
                    })).flatMap(user -> {
                        try {
                            String applicationToken = buildApplicationToken(user.getName(), user.getEmail(), user.getId(), user.getPicture());
                            ResponseCookie responseCookie = ResponseCookie
                                .from("applicationToken", applicationToken)
                                .path("/")
                                .build();
                            exchange.getResponse().beforeCommit(() -> {
                                exchange.getResponse().addCookie(responseCookie);
                                return Mono.empty();
                            });
                            UserDocument userDocument = new UserDocument();
                            userDocument.setId(user.getId());
                            userDocument.setName(user.getName());
                            userDocument.setEmail(user.getEmail());
                            userDocument.setPicture(user.getPicture());
                            userDocument.setCoins(user.getCoins());
                            return Mono.just(userDocument);
                        } catch (JOSEException e) {
                            return Mono.error(new RuntimeException(e));
                        }
                    });
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
