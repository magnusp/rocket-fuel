package se.fortnox.rocketfuel.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import se.fortnox.rocketfuel.api.QuestionResource;
import se.fortnox.rocketfuel.dao.QuestionRepository;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class QuestionController implements QuestionResource {
    private final QuestionRepository questionRepository;

    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Mono<se.fortnox.rocketfuel.api.Question> getQuestion(long questionId) {
        return questionRepository
            .findById(questionId)
            .map(question -> {
                se.fortnox.rocketfuel.api.Question q1 = new se.fortnox.rocketfuel.api.Question();
                q1.setId(question.getId());
                q1.setTitle(question.getTitle());
                q1.setQuestion(question.getQuestion());
                return q1;
            });
    }

    @Override
    public Mono<List<se.fortnox.rocketfuel.api.Question>> getLatestQuestions() {
        se.fortnox.rocketfuel.api.Question question = new se.fortnox.rocketfuel.api.Question();
        question.setTitle("Some stub title");
        List<se.fortnox.rocketfuel.api.Question> questions = new ArrayList<>();
        questions.add(question);
        return Mono.just(questions);
        //return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<List<se.fortnox.rocketfuel.api.Question>> getPopularQuestions() {
        se.fortnox.rocketfuel.api.Question question = new se.fortnox.rocketfuel.api.Question();
        question.setTitle("Some stub title");
        List<se.fortnox.rocketfuel.api.Question> questions = new ArrayList<>();
        questions.add(question);
        return Mono.just(questions);
        //return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<List<se.fortnox.rocketfuel.api.Question>> getPopularUnansweredQuestions() {
        se.fortnox.rocketfuel.api.Question question = new se.fortnox.rocketfuel.api.Question();
        question.setTitle("Some stub title");
        List<se.fortnox.rocketfuel.api.Question> questions = new ArrayList<>();
        questions.add(question);
        return Mono.just(questions);
        //return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<List<se.fortnox.rocketfuel.api.Question>> getRecentlyAcceptedQuestions() {
        se.fortnox.rocketfuel.api.Question question = new se.fortnox.rocketfuel.api.Question();
        question.setTitle("Some stub title");
        List<se.fortnox.rocketfuel.api.Question> questions = new ArrayList<>();
        questions.add(question);
        return Mono.just(questions);
        //return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<Void> createQuestion(se.fortnox.rocketfuel.api.Question question, ServerHttpResponse response) {
        se.fortnox.rocketfuel.dao.entity.Question questionEntity = new se.fortnox.rocketfuel.dao.entity.Question();
        questionEntity.setCreatedAt(OffsetDateTime.now());
        questionEntity.setQuestion(question.getQuestion());
        questionEntity.setTitle(question.getTitle());
        return questionRepository
            .save(questionEntity)
            .flatMap(q1 -> {
                response.setStatusCode(HttpStatus.SEE_OTHER);
                response.getHeaders().setLocation(URI.create("/api/questions/" + q1.getId()));
                return Mono.empty();
            });
    }

    @Override
    public Mono<List<se.fortnox.rocketfuel.api.Question>> getQuestionsBySearchQuery(String searchQuery) {
        se.fortnox.rocketfuel.api.Question question = new se.fortnox.rocketfuel.api.Question();
        question.setTitle("Some stub title");
        List<se.fortnox.rocketfuel.api.Question> questions = new ArrayList<>();
        questions.add(question);
        return Mono.just(questions);
        //return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<List<se.fortnox.rocketfuel.api.Question>> getQuestions(long userId) {
        se.fortnox.rocketfuel.api.Question question = new se.fortnox.rocketfuel.api.Question();
        question.setTitle("Some stub title");
        List<se.fortnox.rocketfuel.api.Question> questions = new ArrayList<>();
        questions.add(question);
        return Mono.just(questions);
        //return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<se.fortnox.rocketfuel.api.Question> updateQuestion(long questionId, se.fortnox.rocketfuel.api.Question question) {
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
    public Mono<se.fortnox.rocketfuel.api.Question> getQuestionById(long questionId) {
        return Mono.error(new UnsupportedOperationException());
    }
}
