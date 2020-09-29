package se.fortnox.rocketfuel.dao;

import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import se.fortnox.rocketfuel.dao.entity.QuestionVotes;

@Component
public class QuestionVotesRepositoryImpl implements QuestionVotesRepository {

    private final DatabaseClient databaseClient;

    public QuestionVotesRepositoryImpl(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @Override
    public Mono<QuestionVotes> findVotesForQuestion(Long questionId) {
        return databaseClient
            .execute("SELECT count(user_id) AS votes FROM question_vote WHERE question_id=:questionId")
            .bind("questionId", questionId)
            .map(row ->  {
                return new QuestionVotes(questionId, row.get("votes", Long.class));
            })
            .one();
    }
}
