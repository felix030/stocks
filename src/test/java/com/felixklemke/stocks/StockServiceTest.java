package com.felixklemke.stocks;

import com.felixklemke.stocks.components.StockService;
import com.felixklemke.stocks.model.Price;
import com.felixklemke.stocks.model.Stock;
import org.junit.jupiter.api.Test;
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
    private StockService stockService;

    @Test
    void givenAValidStock_whenSaved_thenSuccessfullySaved() {
        //GIVEN
        Stock stockToBeSaved = given.validStock();

        //WHEN
        Stock stockReturned = stockService.saveStock(stockToBeSaved);

        //THEN
        then.stockPropertyIsEqual(stockToBeSaved, stockReturned);
        Stock stockFetchedById = stockService.findById(stockReturned.getId()).orElseThrow();
        then.stockPropertyIsEqual(stockToBeSaved, stockFetchedById);
        Stock stockFetchedByExternalId = stockService.findByExternalId(stockReturned.getExternalId()).orElseThrow();
        then.stockPropertyIsEqual(stockToBeSaved, stockFetchedByExternalId);

    }

    @Test
    void givenAValidStockInUsDollar_whenSaved_thenSuccessfullySaved() {
        //GIVEN
        Price price = Price.builder()
                .currentPrice(100L)
                .currencyValue(Price.CurrencyValue.US_DOLLAR_CENT)
                .lastUpdatedBy("Tester")
                .build();

        var stockToBeSaved = Stock.builder()
                .name("TestStockName")
                .externalId(UUID.randomUUID())
                .price(price)
                .build();

        //WHEN
        Stock stockReturned = stockService.saveStock(stockToBeSaved);

        //THEN
        then.stockPropertyIsEqual(stockToBeSaved, stockReturned);
        Stock stockFetchedById = stockService.findById(stockReturned.getId()).orElseThrow();
        then.stockPropertyIsEqual(stockToBeSaved, stockFetchedById);
        Stock stockFetchedByExternalId = stockService.findByExternalId(stockReturned.getExternalId()).orElseThrow();
        then.stockPropertyIsEqual(stockToBeSaved, stockFetchedByExternalId);

    }

    @ParameterizedTest(name = "{index} Given invalid stock properties for {0}")
    @MethodSource("invalidStockArguments")
    void givenInvalidStock_whenSaved_whenThrowException(String testCase, String stockName, UUID externalId, Long currentPrice, String lastUpdatedBy) {
        Price price = Price.builder()
                .currentPrice(currentPrice)
                .lastUpdatedBy(lastUpdatedBy)
                .build();

        var stock = Stock.builder()
                .name(stockName)
                .externalId(externalId)
                .price(price)
                .build();

        assertThrows(ConstraintViolationException.class, () -> stockService.saveStock(stock));
    }

    @Test
    void givenNullPrice_whenStockSaved_thenThrowException() {
        var stock = Stock.builder()
                .name("Test stock")
                .externalId(UUID.randomUUID())
                .price(null)
                .build();

        assertThrows(ConstraintViolationException.class, () -> stockService.saveStock(stock));
    }

    private static Stream<Arguments> invalidStockArguments() {
        return Stream.of(
                Arguments.of("name", "", UUID.randomUUID(), 1L, "Tester"),
                Arguments.of("name", null, UUID.randomUUID(), 1L, "Tester"),
                Arguments.of("externalId", "Test stock name", null, 1L, "Tester"),
                Arguments.of("currentPrice", "Test stock name", UUID.randomUUID(), -1L, "Tester"),
                Arguments.of("currentPrice", "Test stock name", UUID.randomUUID(), null, "Tester"),
                Arguments.of("lastUpdatedBy", "Test stock name", null, 1L, "")
        );
    }
}
