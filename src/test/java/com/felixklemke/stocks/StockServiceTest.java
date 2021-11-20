package com.felixklemke.stocks;

import com.felixklemke.stocks.components.StockService;
import com.felixklemke.stocks.model.Stock;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;


class StockServiceTest extends AbstractIntegrationTest {

    @Autowired
    StockService stockService;

    @ParameterizedTest(name = "{index} Given invalid stock properties for {0}")
    @MethodSource("invalidStockArguments")
    void givenInvalidStockWhenSavedThenThrowException(String testCase, String stockName, UUID externalId, Long currentPrice) {
        var stock = Stock.builder()
                .name(stockName)
                .externalId(externalId)
                .currentPrice(currentPrice)
                .build();


        assertThrows(ConstraintViolationException.class, () -> stockService.saveStock(stock));
    }

    private static Stream<Arguments> invalidStockArguments() {
        return Stream.of(
                Arguments.of("name", "", UUID.randomUUID(), 1L),
                Arguments.of("currentPrice", "Test stock name", UUID.randomUUID(), -1L),
                Arguments.of("currentPrice", "Test stock name", UUID.randomUUID(), null),
                Arguments.of("externalId", "Test stock name", null, 1L)
        );
    }
}
