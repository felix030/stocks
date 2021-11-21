package com.felixklemke.stocks;

import com.felixklemke.stocks.model.Stock;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

class StockManagementIntegrationTest extends AbstractIntegrationTest {

    @Test
    void givenValidListOfStocks_whenGetAllViewableStocksRequested_thenCorrectListReturned() throws Exception {
        //GIVEN
        List<Stock> stocks = given.validListOfStocks();

        //WHEN
        ResultActions resultActions = when.getAllViewableStocks();

        //THEN
        then.resultIsOk(resultActions);
        then.expectedStocksIncludedInResponse(resultActions, stocks);
    }

    @Test
    void givenNoStocksInDb_whenGetAllViewableStocksRequested_thenNothingReturned() throws Exception {
        //WHEN
        ResultActions resultActions = when.getAllViewableStocks();

        //THEN
        then.resultIsOk(resultActions);
        then.noStocksInResponse(resultActions);
    }
}
