package se.fortnox.rocketfuel.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.session.ReactiveMapSessionRepository;
import org.springframework.session.ReactiveSessionRepository;

import java.util.concurrent.ConcurrentHashMap;

/*@Configuration
@EnableSpringWebSession*/
public class MySessionConfiguration {
    @Bean
    public ReactiveSessionRepository reactiveSessionRepository() {
        return new ReactiveMapSessionRepository(new ConcurrentHashMap<>());
    }
}
