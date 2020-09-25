package se.fortnox.rocketfuel.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import se.fortnox.rocketfuel.api.QuestionRepresentation;
import se.fortnox.rocketfuel.api.QuestionResource;
import se.fortnox.rocketfuel.dao.QuestionRepository;
import se.fortnox.rocketfuel.dao.UserRepository;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class QuestionController implements QuestionResource {
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public QuestionController(QuestionRepository questionRepository, UserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Mono<QuestionRepresentation> getQuestion(long questionId) {
        return questionRepository
            .findById(questionId)
            .flatMap(question -> {
                return userRepository.findById(question.getUserId())
                    .map(user -> {
                        return new QuestionRepresentation(
                            question.getId(),
                            question.getQuestion(),
                            question.getTitle(),
                            question.getBounty(),
                            question.getCreatedAt(),
                            question.getAnswerAccepted(),
                            question.getSlackId(),
                            user.getName(),
                            user.getPicture()
                        );
                    });
            });
    }

    @Override
    public Mono<List<QuestionRepresentation>> getLatestQuestions() {
        QuestionRepresentation questionRepresentation = new QuestionRepresentation();
        questionRepresentation.setTitle("Some stub title");
        List<QuestionRepresentation> questionRepresentations = new ArrayList<>();
        questionRepresentations.add(questionRepresentation);
        return Mono.just(questionRepresentations);
        //return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<List<QuestionRepresentation>> getPopularQuestions() {
        QuestionRepresentation questionRepresentation = new QuestionRepresentation();
        questionRepresentation.setTitle("Some stub title");
        List<QuestionRepresentation> questionRepresentations = new ArrayList<>();
        questionRepresentations.add(questionRepresentation);
        return Mono.just(questionRepresentations);
        //return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<List<QuestionRepresentation>> getPopularUnansweredQuestions() {
        QuestionRepresentation questionRepresentation = new QuestionRepresentation();
        questionRepresentation.setTitle("Some stub title");
        List<QuestionRepresentation> questionRepresentations = new ArrayList<>();
        questionRepresentations.add(questionRepresentation);
        return Mono.just(questionRepresentations);
        //return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<List<QuestionRepresentation>> getRecentlyAcceptedQuestions() {
        QuestionRepresentation questionRepresentation = new QuestionRepresentation();
        questionRepresentation.setTitle("Some stub title");
        List<QuestionRepresentation> questionRepresentations = new ArrayList<>();
        questionRepresentations.add(questionRepresentation);
        return Mono.just(questionRepresentations);
        //return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<Void> createQuestion(QuestionRepresentation questionRepresentation, ServerHttpResponse response) {
        se.fortnox.rocketfuel.dao.entity.Question questionEntity = new se.fortnox.rocketfuel.dao.entity.Question();
        questionEntity.setCreatedAt(OffsetDateTime.now());
        questionEntity.setQuestion(questionRepresentation.getQuestion());
        questionEntity.setTitle(questionRepresentation.getTitle());
        return questionRepository
            .save(questionEntity)
            .flatMap(q1 -> {
                response.setStatusCode(HttpStatus.SEE_OTHER);
                response.getHeaders().setLocation(URI.create("/api/questions/" + q1.getId()));
                return Mono.empty();
            });
    }

    @Override
    public Mono<List<QuestionRepresentation>> getQuestionsBySearchQuery(String searchQuery) {
        QuestionRepresentation questionRepresentation = new QuestionRepresentation();
        questionRepresentation.setTitle("Some stub title");
        List<QuestionRepresentation> questionRepresentations = new ArrayList<>();
        questionRepresentations.add(questionRepresentation);
        return Mono.just(questionRepresentations);
        //return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<List<QuestionRepresentation>> getQuestions(long userId) {
        QuestionRepresentation questionRepresentation = new QuestionRepresentation();
        questionRepresentation.setTitle("Some stub title");
        List<QuestionRepresentation> questionRepresentations = new ArrayList<>();
        questionRepresentations.add(questionRepresentation);
        return Mono.just(questionRepresentations);
        //return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<QuestionRepresentation> updateQuestion(long questionId, QuestionRepresentation questionRepresentation) {
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
    public Mono<QuestionRepresentation> getQuestionById(long questionId) {
        return Mono.error(new UnsupportedOperationException());
    }
}
