package se.fortnox.rocketfuel.dao;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import se.fortnox.rocketfuel.dao.entity.Question;

import java.time.OffsetDateTime;

@Component
public interface QuestionRepository extends ReactiveCrudRepository<Question, Long> {
    interface Question {
        long getId();
        String getTitle();
        String getQuestion();
        OffsetDateTime getCreatedAt();
        Long getUserId();
        boolean isAnswerAccepted();
        String getSlackId();
    }
}
