package com.felixklemke.stocks.components;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.Clock;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider", auditorAwareRef = "auditorAware")
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }

    @Bean
    StocksDateTimeProvider auditingDateTimeProvider(Clock clock) {
        return new StocksDateTimeProvider(clock);
    }
    @Bean
    Clock clock(){
        return Clock.systemDefaultZone();
    }
}
