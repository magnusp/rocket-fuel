package se.fortnox.rocketfuel;

import liquibase.exception.LiquibaseException;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.IOException;

public class TestInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static PostgreSQLContainer sqlContainer;
    private static Migrator migrator = new Migrator();

    static {
        sqlContainer = new PostgreSQLContainer("postgres:10.7")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa");
        sqlContainer.start();
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        migrator.setJdbcUrl(sqlContainer.getJdbcUrl());
        migrator.setUsername(sqlContainer.getUsername());
        migrator.setPassword(sqlContainer.getPassword());
        try {
            migrator.setup();
            migrator.run();

            String url = "r2dbc:postgresql://" + sqlContainer.getContainerIpAddress() + ":" + sqlContainer.getFirstMappedPort() + "/" + sqlContainer.getDatabaseName();
            TestPropertyValues.of(
                "spring.r2dbc.url=" + url,
                "spring.r2dbc.username=" + sqlContainer.getUsername(),
                "spring.r2dbc.password=" + sqlContainer.getPassword()
            ).applyTo(applicationContext.getEnvironment());
        } catch (LiquibaseException | IOException e) {
            e.printStackTrace();
        }



    }
}
