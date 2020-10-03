package se.fortnox.rocketfuel.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import se.fortnox.rocketfuel.api.QuestionDocument;
import se.fortnox.rocketfuel.api.QuestionResource;
import se.fortnox.rocketfuel.authentication.Auth;
import se.fortnox.rocketfuel.dao.QuestionRepository;
import se.fortnox.rocketfuel.dao.QuestionVotesRepository;
import se.fortnox.rocketfuel.dao.UserRepository;
import se.fortnox.rocketfuel.dao.entity.Question;
import se.fortnox.rocketfuel.dao.entity.QuestionVotes;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class QuestionController implements QuestionResource {
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final QuestionVotesRepository questionVotesRepository;

    public QuestionController(QuestionRepository questionRepository, UserRepository userRepository, QuestionVotesRepository questionVotesRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.questionVotesRepository = questionVotesRepository;
    }

    @Override
    public Mono<QuestionDocument> getQuestion(long questionId) {
        return Mono.zip(
            questionRepository.findById(questionId),
            questionVotesRepository.findVotesForQuestion(questionId)
        ).flatMap(objects ->
            userRepository
                .findById(objects.getT1().getUserId())
                .map(user -> {
                    Question question = objects.getT1();
                    QuestionVotes questionVotes = objects.getT2();

                    return new QuestionDocument(
                        question.getQuestion(),
                        question.getTitle(),
                        user.getName(),
                        user.getId(),
                        question.getCreatedAt(),
                        questionVotes.getVotes(),
                        question.getAnswerAccepted(),
                        question.getId(),
                        user.getPicture()
                    );
                })
        );
    }

    @Override
    public Mono<List<QuestionDocument>> getLatestQuestions() {
        QuestionDocument questionDocument = new QuestionDocument();
        questionDocument.setTitle("Some stub title");
        List<QuestionDocument> questionDocuments = new ArrayList<>();
        questionDocuments.add(questionDocument);
        return Mono.just(questionDocuments);
        //return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<List<QuestionDocument>> getPopularQuestions() {
        QuestionDocument questionDocument = new QuestionDocument();
        questionDocument.setTitle("Some stub title");
        List<QuestionDocument> questionDocuments = new ArrayList<>();
        questionDocuments.add(questionDocument);
        return Mono.just(questionDocuments);
        //return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<List<QuestionDocument>> getPopularUnansweredQuestions() {
        QuestionDocument questionDocument = new QuestionDocument();
        questionDocument.setTitle("Some stub title");
        List<QuestionDocument> questionDocuments = new ArrayList<>();
        questionDocuments.add(questionDocument);
        return Mono.just(questionDocuments);
        //return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<List<QuestionDocument>> getRecentlyAcceptedQuestions() {
        QuestionDocument questionDocument = new QuestionDocument();
        questionDocument.setTitle("Some stub title");
        List<QuestionDocument> questionDocuments = new ArrayList<>();
        questionDocuments.add(questionDocument);
        return Mono.just(questionDocuments);
        //return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<Void> createQuestion(Auth auth, QuestionDocument questionDocument, ServerHttpResponse response) {
        se.fortnox.rocketfuel.dao.entity.Question questionEntity = new se.fortnox.rocketfuel.dao.entity.Question();
        questionEntity.setCreatedAt(OffsetDateTime.now());
        questionEntity.setQuestion(questionDocument.getQuestion());
        questionEntity.setTitle(questionDocument.getTitle());
        return questionRepository
            .save(questionEntity)
            .flatMap(q1 -> {
                response.setStatusCode(HttpStatus.SEE_OTHER);
                response.getHeaders().setLocation(URI.create("/api/questions/" + q1.getId()));
                return Mono.empty();
            });
    }

    @Override
    public Mono<List<QuestionDocument>> getQuestionsBySearchQuery(String searchQuery) {
        QuestionDocument questionDocument = new QuestionDocument();
        questionDocument.setTitle("Some stub title");
        List<QuestionDocument> questionDocuments = new ArrayList<>();
        questionDocuments.add(questionDocument);
        return Mono.just(questionDocuments);
        //return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<List<QuestionDocument>> getQuestions(long userId) {
        QuestionDocument questionDocument = new QuestionDocument();
        questionDocument.setTitle("Some stub title");
        List<QuestionDocument> questionDocuments = new ArrayList<>();
        questionDocuments.add(questionDocument);
        return Mono.just(questionDocuments);
        //return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<QuestionDocument> updateQuestion(long questionId, QuestionDocument questionDocument) {
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
    public Mono<QuestionDocument> getQuestionById(long questionId) {
        return Mono.error(new UnsupportedOperationException());
    }
}
