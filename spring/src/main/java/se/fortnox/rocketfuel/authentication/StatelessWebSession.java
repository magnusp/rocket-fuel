package se.fortnox.rocketfuel.authentication;

import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

class StatelessWebSession implements WebSession, Serializable {
    private final HashMap<String, Object> attributes = new HashMap<String, Object>();
    private final Instant instantStarted;

    public StatelessWebSession(Instant now) {
        instantStarted = now;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public void start() {
        int i = 0;
    }

    @Override
    public boolean isStarted() {
        return false;
    }

    @Override
    public Mono<Void> changeSessionId() {
        return null;
    }

    @Override
    public Mono<Void> invalidate() {
        return null;
    }

    @Override
    public Mono<Void> save() {
        return null;
    }

    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public Instant getCreationTime() {
        return null;
    }

    @Override
    public Instant getLastAccessTime() {
        return null;
    }

    @Override
    public void setMaxIdleTime(Duration maxIdleTime) {

    }

    @Override
    public Duration getMaxIdleTime() {
        return null;
    }
}
