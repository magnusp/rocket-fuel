package se.fortnox.rocketfuel.dao;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import se.fortnox.rocketfuel.dao.entity.User;

import javax.validation.constraints.NotNull;

@Component
public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    @Override
    @Query("SELECT \"user\".* FROM \"user\" WHERE \"user\".id=:id")
    Mono<User> findById(Long id);

    @Query("SELECT \"user\".* FROM \"user\" WHERE \"user\".email=:email")
    Mono<User> findByEmail(@NotNull String email);

    @Query("INSERT INTO \"user\" (email, name, picture) VALUES (:email, :name, :picture) RETURNING *")
    Mono<User> save(String email, String name, String picture); // TODO: Check how this is done with pojos
}
