package se.fortnox.rocketfuel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.http.server.reactive.MockServerHttpResponse;
import org.springframework.test.context.ContextConfiguration;
import se.fortnox.rocketfuel.api.QuestionDocument;
import se.fortnox.rocketfuel.api.QuestionResource;
import se.fortnox.rocketfuel.authentication.Auth;

import static org.mockito.Mockito.mock;

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
        QuestionDocument questionDocument = new QuestionDocument();
        questionDocument.setTitle("Hej");
        questionDocument.setQuestion("Question");

        questionResource.createQuestion(mock(Auth.class), questionDocument, response)
            .doOnSuccess(aVoid -> {
                System.out.println("Created");
            })
            .block();
        QuestionDocument block = questionResource.getQuestion(1)
            .block();
        System.out.println(block);
    }

}
