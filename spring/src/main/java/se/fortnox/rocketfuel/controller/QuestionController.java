package se.fortnox.rocketfuel.controller;

import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import se.fortnox.rocketfuel.api.Question;
import se.fortnox.rocketfuel.api.QuestionResource;
import se.fortnox.rocketfuel.dao.QuestionRepository;
import se.fortnox.rocketfuel.document.CreateQuestion;

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
    public Mono<se.fortnox.rocketfuel.document.Question> getQuestion(long questionId) {
       return questionRepository.findById(questionId).map(entity -> {
           se.fortnox.rocketfuel.document.Question representation = new se.fortnox.rocketfuel.document.Question();
           representation.setId(entity.getId());
           representation.setTitle(entity.getTitle());
           representation.setQuestion(entity.getQuestion());
           representation.setCreatedAt(entity.getCreatedAt());
           return representation;
       });
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
    public Mono<Question> createQuestion(CreateQuestion doc) {
        se.fortnox.rocketfuel.dao.entity.Question question = new se.fortnox.rocketfuel.dao.entity.Question();
        question.setTitle(doc.getTitle());
        question.setQuestion(doc.getBody());
        question.setCreatedAt(OffsetDateTime.now());
        return questionRepository.save(question).map(entity -> {
            Question representation = new Question();
            representation.setId(entity.getId());
            representation.setTitle(entity.getTitle());
            representation.setQuestion(entity.getQuestion());
            return representation;
        });
        /*return questionRepository
            .addQuestion(1, question.getQuestion(), question.getTitle(), 0)
            .flatMap(aLong -> {
                return questionRepository.findById(aLong);
            });*/
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
