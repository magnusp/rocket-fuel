package se.fortnox.rocketfuel;

import io.r2dbc.spi.ConnectionFactory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.core.ReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.mapping.R2dbcMappingContext;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import se.fortnox.rocketfuel.api.AnswerDocument;
import se.fortnox.rocketfuel.controller.AnswerController;
import se.fortnox.rocketfuel.dao.AnswerRepository;
import se.fortnox.rocketfuel.dao.QuestionRepository;
import se.fortnox.rocketfuel.dao.QuestionVotesRepository;
import se.fortnox.rocketfuel.dao.UserRepository;
import se.fortnox.rocketfuel.dao.entity.Answer;
import se.fortnox.rocketfuel.dao.entity.Question;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Component
public class RocketfuelSteps extends AbstractRocketFuelSteps {

    Map<String, Question> questionStore = new HashMap<>();

    @Before
    public void setup() {
    }
    @Given("that a question \"Stub question\" exists")
    @Pending
    public void givenThatAQuestionStubQuestionExists() {
        Question question = addQuestion(1L, "Stub question");
        when(questionRepository.findById(anyLong())).thenReturn(Mono.just(question));
    }

    private Question addQuestion(long id, String title) {
        return questionStore.computeIfAbsent(title, s -> {
            Question question = new Question();
            question.setId(id);
            question.setTitle(title);
            return question;
        });
    }

    @When("\"Stub question\" gets an answer \"Stub answer\"")
    @Pending
    public void whenStubQuestionGetsAnAnswerStubAnswer() {
        var question = questionStore.get("Stub question");
        var answerDocument = new AnswerDocument();
        answerDocument.setAnswer("Stub answer");
        answerController
            .createAnswer(answerDocument, question.getId())
            .as(StepVerifier::create)
        .verifyComplete();
    }

    @When("that the creator of \"Stub question\" marks \"Stub answer\" as accepted")
    @Pending
    public void whenThatTheCreatorOfStubQuestionMarksStubAnswerAsAccepted() {
        answerController
            .markAsAcceptedAnswer(1L)
        .as(StepVerifier::create)
        .verifyComplete();
    }

    @Then("question \"Stub question\" should be marked as accepted")
    @Pending
    public void thenQuestionStubQuestionShouldBeMarkedAsAccepted() {
        var question = questionStore.get("Stub question");

    }

    @Then("answer \"Stub answer\" should be marked as accepted")
    @Pending
    public void thenAnswerStubAnswerShouldBeMarkedAsAccepted() {
        // PENDING
    }

}
