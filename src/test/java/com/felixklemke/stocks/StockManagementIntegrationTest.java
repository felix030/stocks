package com.felixklemke.stocks;

import com.felixklemke.stocks.model.Stock;
import com.felixklemke.stocks.model.WebCurrencyValue;
import com.felixklemke.stocks.model.WebStockRequestBody;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.stream.Stream;

class StockManagementIntegrationTest extends AbstractIntegrationTest {

    //GET TESTS
    @Test
    void givenValidListOfStocks_whenGetAllViewableStocksRequested_thenCorrectListReturned() throws Exception {
        //GIVEN
        List<Stock> stocks = given.validListOfStocks();
        //WHEN
        ResultActions resultActions = when.allViewableStocks();
        //THEN
        then.resultIsOk(resultActions);
        then.expectedStocksIncludedInResponse(resultActions, stocks);
    }

    @Test
    void givenNoStocksInDb_whenGetAllViewableStocksRequested_thenNothingReturned() throws Exception {
        //WHEN
        ResultActions resultActions = when.allViewableStocks();
        //THEN
        then.resultIsOk(resultActions);
        then.noStocksInResponse(resultActions);
    }

    //POST TESTS
    @Test
    void givenAStockCreationRequest_whenCreateStockCalled_thenStockCreated() throws Exception {
        //GIVEN
        WebStockRequestBody creationRequest = given.validStockCreationRequest();
        //WHEN
        ResultActions resultActions = when.createStock(creationRequest);
        //THEN
        then.resultIsOk(resultActions);
        then.stockCreationSuccessful(resultActions);
    }

    @ParameterizedTest(name = "{index} Given invalid stock request properties for {0}")
    @MethodSource("invalidStockRequestArguments")
    void givenInvalidStockCreationRequest_whenCreateStockCalled_thenErrorReturned(String testCase, String stockName,
                                                                                  Long price, WebCurrencyValue currencyValue) throws Exception {
        //GIVEN
        WebStockRequestBody creationRequest = new WebStockRequestBody()
                .name(stockName)
                .price(price)
                .currencyValue(currencyValue);

        //WHEN
        ResultActions resultActions = when.createStock(creationRequest);

        //THEN
        then.resultIsBadRequest(resultActions);
    }

    //PUT TESTS



    private static Stream<Arguments> invalidStockRequestArguments() {
        return Stream.of(
                Arguments.of("empty name", "", 1L, WebCurrencyValue.EURO_CENT),
                Arguments.of("null name", null, 1L, WebCurrencyValue.US_DOLLAR_CENT),
                Arguments.of("negative price", "Test stock name", -1L, WebCurrencyValue.EURO_CENT),
                Arguments.of("null as price", "Test stock name", null, WebCurrencyValue.EURO_CENT)
        );
    }



}
