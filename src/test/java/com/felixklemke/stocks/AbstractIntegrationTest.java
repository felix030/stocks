package com.felixklemke.stocks;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest
@ContextConfiguration(initializers = com.felixklemke.stocks.AbstractIntegrationTest.Initializer.class)
public abstract class AbstractIntegrationTest {

    private static final int REDIS_PORT = 6379;

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
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
