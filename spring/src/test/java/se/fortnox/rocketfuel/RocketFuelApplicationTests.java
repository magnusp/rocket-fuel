package se.fortnox.rocketfuel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.http.server.reactive.MockServerHttpResponse;
import org.springframework.test.context.ContextConfiguration;
import se.fortnox.rocketfuel.api.QuestionRepresentation;
import se.fortnox.rocketfuel.api.QuestionResource;

@SpringBootTest
@ContextConfiguration(initializers = {TestInitializer.class})
class RocketFuelApplicationTests {

    private final QuestionResource questionResource;

    @Autowired
    public RocketFuelApplicationTests(QuestionResource questionResource) {
        this.questionResource = questionResource;
    }

    @Test
    void contextLoads() {
        System.out.println("Context loaded");
        MockServerHttpResponse response = new MockServerHttpResponse();
        QuestionRepresentation questionRepresentation = new QuestionRepresentation();
        questionRepresentation.setTitle("Hej");
        questionRepresentation.setQuestion("Question");

        questionResource.createQuestion(questionRepresentation, response)
            .doOnSuccess(aVoid -> {
                System.out.println("Created");
            })
            .block();
        QuestionRepresentation block = questionResource.getQuestion(1)
            .block();
        System.out.println(block);
    }

}
