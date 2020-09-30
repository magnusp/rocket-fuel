package se.fortnox.rocketfuel.authentication;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.http.HttpCookie;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import org.springframework.web.server.session.WebSessionManager;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import se.fortnox.rocketfuel.dao.entity.User;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

@Component("webSessionManager")
public class StatelessWebSessionManager implements WebSessionManager {
    private Clock clock = Clock.system(ZoneId.of("GMT"));
    private static final List<SimpleGrantedAuthority> DEFAULT_GRANTED_AUTHORITIES = List.of(new SimpleGrantedAuthority("User"));

    @Override
    public Mono<WebSession> getSession(ServerWebExchange exchange) {
        return Mono.defer(() -> retrieveSession(exchange)
            .switchIfEmpty(createWebSession())
            .doOnNext(session -> exchange.getResponse().beforeCommit(() -> save(exchange, session))));
    }

    private Mono<Void> save(ServerWebExchange exchange, WebSession session) {
        return Mono.empty();
    }

    private Mono<WebSession> createWebSession() {

        // Opportunity to clean expired sessions
            Instant now = this.clock.instant();

        return Mono.fromSupplier(() -> new StatelessWebSession(now))
            .subscribeOn(Schedulers.boundedElastic())
            .cast(WebSession.class);
    }

    private Mono<WebSession> retrieveSession(ServerWebExchange exchange) {
        HttpCookie httpCookie = exchange.getRequest().getCookies().getFirst("applicationToken");
        StatelessWebSession statelessWebSession = new StatelessWebSession(clock.instant());
        if (httpCookie == null || httpCookie.getValue().equals("value")) {
            return Mono.just(statelessWebSession);
        }

        // Generate random 256-bit (32-byte) shared secret
        byte[] sharedSecret = "a really long secret quzbar a really long secret quzbar a really long secret quzbar".getBytes(StandardCharsets.UTF_8);

        // Create HMAC signer
        SignedJWT signedJWT;
        String name;
        String email;
        String picture;
        Long userId;
        try {
            signedJWT = SignedJWT.parse(httpCookie.getValue());

            JWSVerifier verifier = new MACVerifier(sharedSecret);
            signedJWT.verify(verifier);
            JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();
            name = jwtClaimsSet.getStringClaim("name");
            email = jwtClaimsSet.getStringClaim("email");
            picture = jwtClaimsSet.getStringClaim("picture");
            userId = jwtClaimsSet.getLongClaim("id");
        } catch (ParseException | JOSEException e) {
            return Mono.error(e);
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPicture(picture);
        user.setId(userId);
        user.setCoins(0L);
        PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken = new PreAuthenticatedAuthenticationToken(
            user,
            signedJWT,
            DEFAULT_GRANTED_AUTHORITIES
        );
        SecurityContextImpl securityContext = new SecurityContextImpl(preAuthenticatedAuthenticationToken);
        statelessWebSession.getAttributes().putIfAbsent(
            WebSessionServerSecurityContextRepository.DEFAULT_SPRING_SECURITY_CONTEXT_ATTR_NAME,
            securityContext
        );
        return Mono.just(statelessWebSession);
    }

}
