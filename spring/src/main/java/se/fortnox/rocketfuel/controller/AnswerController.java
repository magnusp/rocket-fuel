package se.fortnox.rocketfuel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import se.fortnox.rocketfuel.api.Answer;
import se.fortnox.rocketfuel.api.AnswerResource;
import se.fortnox.rocketfuel.dao.AnswerRepository;

import java.util.List;

@RestController
public class AnswerController implements AnswerResource {

    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerController(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public Mono<Answer> createAnswer(Answer answer, long questionId) {
        return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<List<Answer>> getAnswers(long questionId) {
        return answerRepository.findByQuestionId(questionId).collectList();
    }

    @Override
    public Mono<Void> markAsAcceptedAnswer(long answerId) {
        return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<Void> updateAnswer(long answerId, Answer answer) {
        return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<Void> deleteAnswer(long answerId) {
        return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<Void> upVoteAnswer(long answerId) {
        return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<Void> downVoteAnswer(long answerId) {
        return Mono.error(new UnsupportedOperationException());
    }
}
