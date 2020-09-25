package se.fortnox.rocketfuel.dao;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import se.fortnox.rocketfuel.dao.entity.User;

@Component
public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    @Override
    @Query("SELECT \"user\".* FROM \"user\" WHERE \"user\".id=:id")
    Mono<User> findById(Long id);
}
