package se.fortnox.rocketfuel.controller;

import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import se.fortnox.rocketfuel.api.Question;
import se.fortnox.rocketfuel.api.QuestionResource;

import java.util.List;

@RestController
public class QuestionController implements QuestionResource {
    @Override
    public Mono<Question> getQuestionBySlackThreadId(String slackId) {
        return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<Question> getQuestion(long questionId) {
        return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<List<Question>> getLatestQuestions() {
        return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<List<Question>> getPopularQuestions() {
        return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<List<Question>> getPopularUnansweredQuestions() {
        return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<List<Question>> getRecentlyAcceptedQuestions() {
        return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<Question> createQuestion(Question question) {
        return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<List<Question>> getQuestionsBySearchQuery(String searchQuery) {
        return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<List<Question>> getQuestions(long userId) {
        return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<Question> updateQuestion(long questionId, Question question) {
        return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<Void> deleteQuestion(long questionId) {
        return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<Void> upVoteQuestion(long questionId) {
        return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<Void> downVoteQuestion(long questionId) {
        return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<Question> getQuestionById(long questionId) {
        return Mono.error(new UnsupportedOperationException());
    }
}
