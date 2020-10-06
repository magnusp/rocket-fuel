package se.fortnox.rocketfuel;

import io.r2dbc.spi.ConnectionFactory;
import org.jbehave.core.annotations.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.core.ReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.mapping.R2dbcMappingContext;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.stereotype.Component;
import se.fortnox.rocketfuel.controller.AnswerController;
import se.fortnox.rocketfuel.dao.AnswerRepository;
import se.fortnox.rocketfuel.dao.QuestionRepository;
import se.fortnox.rocketfuel.dao.QuestionVotesRepository;
import se.fortnox.rocketfuel.dao.UserRepository;

import static org.mockito.Mockito.mock;

@Component
public class TestSteps {
    @MockBean
    AnswerRepository answerRepository;

    @MockBean
    QuestionRepository questionRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    QuestionVotesRepository questionVotesRepository;

    @MockBean
    ReactiveJwtDecoder reactiveJwtDecoder;

    @Bean
    ConnectionFactory connectionFactory() {
        return mock(ConnectionFactory.class);
    }

    @MockBean
    DatabaseClient r2dbcDatabaseClient;

    @MockBean
    R2dbcMappingContext r2dbcMappingContext;

    @MockBean
    ReactiveDataAccessStrategy reactiveDataAccessStrategy;

    @MockBean
    R2dbcCustomConversions r2dbcCustomConversions;

    @Autowired
    AnswerController answerController;

    @Given("that something")
    public void givenThatSomething() {
        int i = 0;
    }
}
