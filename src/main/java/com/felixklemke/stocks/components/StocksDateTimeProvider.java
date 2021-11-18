package com.felixklemke.stocks.components;

import org.springframework.data.auditing.DateTimeProvider;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

public class StocksDateTimeProvider implements DateTimeProvider {

    private final Clock clock;

    public StocksDateTimeProvider(Clock clock) {
        this.clock = clock;
    }

    @Override
    public Optional<TemporalAccessor> getNow() {
        return Optional.of(OffsetDateTime.now(clock));
    }
}
