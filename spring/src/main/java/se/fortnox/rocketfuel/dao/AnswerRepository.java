package se.fortnox.rocketfuel.dao;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import se.fortnox.rocketfuel.api.Answer;

public interface AnswerRepository extends ReactiveCrudRepository<Answer, Long> {
    @Query("SELECT * FROM answer WHERE question_id=:questionId")
    Flux<Answer> findByQuestionId(long questionId);
}
