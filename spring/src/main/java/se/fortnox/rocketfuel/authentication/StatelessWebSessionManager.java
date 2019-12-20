package se.fortnox.rocketfuel.authentication;

import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import org.springframework.web.server.session.WebSessionManager;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URLEncoder;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.US_ASCII;

@Component("webSessionManager")
public class StatelessWebSessionManager implements WebSessionManager {
    private Clock clock = Clock.system(ZoneId.of("GMT"));

    @Override
    public Mono<WebSession> getSession(ServerWebExchange exchange) {
        return Mono.defer(() -> retrieveSession(exchange)
            .switchIfEmpty(createWebSession())
            .doOnNext(session -> exchange.getResponse().beforeCommit(() -> save(exchange, session))));
    }

    private Mono<Void> save(ServerWebExchange exchange, WebSession session) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            new ObjectOutputStream(baos).writeObject(session);
        } catch (IOException e) {
            return Mono.error(e);
        }

        String serialized = Base64.getUrlEncoder().encodeToString(baos.toByteArray());
        serialized = URLEncoder.encode(serialized, US_ASCII).substring(0, serialized.length()-10);
        ResponseCookie responseCookie = ResponseCookie
            .from("SESSION", serialized)
            .build();
        exchange
            .getResponse()
            .addCookie(responseCookie);
        return Mono.empty();
        //return Mono.empty(); // implement session serialization to cookie
    }

    private Mono<WebSession> createWebSession() {

        // Opportunity to clean expired sessions
        Instant now = this.clock.instant();

        return Mono.fromSupplier(() -> new StatelessWebSession(now))
            .subscribeOn(Schedulers.boundedElastic())
            .cast(WebSession.class);
    }

    private Mono<WebSession> retrieveSession(ServerWebExchange exchange) {
        HttpCookie httpCookie = exchange.getRequest().getCookies().getFirst("SESSION");
        if(httpCookie == null) {
            return Mono.empty(); // Implement decoding from exchange
        }
        if(httpCookie.getValue().equals("value")) {
            return Mono.just(new StatelessWebSession(clock.instant()));
        }
        try {
            byte[] serializedSession = Base64.getUrlDecoder().decode(httpCookie.getValue().replaceAll("\"", ""));
            ByteArrayInputStream bi = new ByteArrayInputStream(serializedSession);

            ObjectInputStream si = new ObjectInputStream(bi);
            return Mono.just((StatelessWebSession) si.readObject());
        } catch (IOException | ClassNotFoundException e) {
            return Mono.just(new StatelessWebSession(clock.instant()));
        }
    }

}
