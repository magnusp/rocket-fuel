package se.fortnox.rocketfuel.dao;

import reactor.core.publisher.Mono;
import se.fortnox.rocketfuel.dao.entity.QuestionVotes;


public interface QuestionVotesRepository  {
    Mono<QuestionVotes> findVotesForQuestion(Long questionId);
}
