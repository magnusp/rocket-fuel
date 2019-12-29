package se.fortnox.rocketfuel.dao;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import se.fortnox.rocketfuel.api.Question;

public interface QuestionRepository extends ReactiveCrudRepository<Question, Long> {
    @Query(
        value =
            "SELECT " +
                "question.id, " +
                "question.question, " +
                "answer_accepted, " +
                "question.title, " +
                "question.bounty, " +
                "question.created_at, " +
                "question.user_id, " +
                "question.slack_id, \"user\".picture, \"user\".name as created_by, " +
                "(SELECT COALESCE(SUM(question_vote.value), 0) FROM question_vote WHERE question_vote.question_id = question.id) AS votes " +
                "FROM " +
                "question " +
                "INNER JOIN " +
                "\"user\" on \"user\".id = question.user_id WHERE question.user_id=:userId " +
                "ORDER BY " +
                "question.created_at DESC")
    Mono<Question> findById(long userId);

    @Query(
        "INSERT INTO " +
            "question (" +
            "question, " +
            "title, " +
            "bounty, " +
            "created_at, " +
            "user_id, " +
            "slack_id) " +
            " VALUES" +
            "(" +
            ":question.question, " +
            ":question.title, " +
            ":question.bounty, " +
            "NOW(), " +
            ":userId, " +
            ":question.slackId" +
            ")")
    Mono<Long> addQuestion(long userId, Question question);
}
