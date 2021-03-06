package com.felixklemke.stocks;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = com.felixklemke.stocks.AbstractIntegrationTest.Initializer.class)
public abstract class AbstractIntegrationTest {

    @Autowired
    protected StocksGiven given;
    @Autowired
    protected StocksWhen when;
    @Autowired
    protected StocksThen then;

    @BeforeEach
    void cleanUp() {
        given.dropDbContent();
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final int REDIS_PORT = 6379;

        static GenericContainer redis = new GenericContainer<>("redis:6-alpine")
                .withExposedPorts(REDIS_PORT)
                .withReuse(true);

        @Override
        public void initialize(ConfigurableApplicationContext context) {
            redis.start();

            String redisContainerIP = "spring.redis.host=" + redis.getContainerIpAddress();
            String redisContainerPort = "spring.redis.port=" + redis.getMappedPort(REDIS_PORT); //assigning the random port
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(context,  redisContainerIP, redisContainerPort); //overriding configuration in runtime
        }
    }
}
