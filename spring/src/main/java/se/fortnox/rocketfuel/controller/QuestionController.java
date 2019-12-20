package se.fortnox.rocketfuel.controller;

import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import se.fortnox.rocketfuel.api.Question;
import se.fortnox.rocketfuel.api.QuestionResource;

import java.util.ArrayList;
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
        Question question = new Question();
        question.setTitle("Some stub title");
        List<Question> questions = new ArrayList<>();
        questions.add(question);
        return Mono.just(questions);
        //return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<List<Question>> getPopularQuestions() {
        Question question = new Question();
        question.setTitle("Some stub title");
        List<Question> questions = new ArrayList<>();
        questions.add(question);
        return Mono.just(questions);
        //return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<List<Question>> getPopularUnansweredQuestions() {
        Question question = new Question();
        question.setTitle("Some stub title");
        List<Question> questions = new ArrayList<>();
        questions.add(question);
        return Mono.just(questions);
        //return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<List<Question>> getRecentlyAcceptedQuestions() {
        Question question = new Question();
        question.setTitle("Some stub title");
        List<Question> questions = new ArrayList<>();
        questions.add(question);
        return Mono.just(questions);
        //return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<Question> createQuestion(Question question) {
        return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<List<Question>> getQuestionsBySearchQuery(String searchQuery) {
        Question question = new Question();
        question.setTitle("Some stub title");
        List<Question> questions = new ArrayList<>();
        questions.add(question);
        return Mono.just(questions);
        //return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<List<Question>> getQuestions(long userId) {
        Question question = new Question();
        question.setTitle("Some stub title");
        List<Question> questions = new ArrayList<>();
        questions.add(question);
        return Mono.just(questions);
        //return Mono.error(new UnsupportedOperationException());
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
