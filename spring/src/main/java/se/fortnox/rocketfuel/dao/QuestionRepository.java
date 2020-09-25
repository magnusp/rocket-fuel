package se.fortnox.rocketfuel.dao;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import se.fortnox.rocketfuel.dao.entity.Question;

@Component
public interface QuestionRepository extends ReactiveCrudRepository<Question, Long> {}
