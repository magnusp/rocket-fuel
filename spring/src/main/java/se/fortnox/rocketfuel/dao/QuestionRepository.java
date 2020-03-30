package se.fortnox.rocketfuel.dao;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import se.fortnox.rocketfuel.dao.entity.Question;

public interface QuestionRepository extends ReactiveCrudRepository<Question, Long> {

}
