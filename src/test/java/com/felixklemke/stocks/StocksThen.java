package com.felixklemke.stocks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.felixklemke.stocks.model.Stock;
import com.felixklemke.stocks.model.WebStocksResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class StocksThen {

    @Autowired
    private ObjectMapper objectMapper;

    protected void resultIsOk(ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isOk());
    }

    protected void stockPropertyIsEqual(Stock initialStock, Stock fetchedStock) {
        assertThat(fetchedStock.getId()).isNotNull();
        assertThat(fetchedStock.getName()).isEqualTo(initialStock.getName());
        assertThat(fetchedStock.getExternalId()).isEqualTo(initialStock.getExternalId());
        assertThat(fetchedStock.getPrice().getCurrentPrice()).isEqualTo(initialStock.getPrice().getCurrentPrice());
        assertThat(fetchedStock.getPrice().getLastUpdatedBy()).isEqualTo(initialStock.getPrice().getLastUpdatedBy());
        assertThat(fetchedStock.getPrice().getCurrencyValue()).isEqualTo(initialStock.getPrice().getCurrencyValue());
    }

    protected void expectedStocksIncludedInResponse(ResultActions resultActions, List<Stock> stocksInDb) throws Exception {
        var response = objectMapper.readValue(getContentString(resultActions), WebStocksResponse.class);
        response.getStocks().forEach(stockResponseItem -> {
            Stock stockReferencedInDb = stocksInDb.stream()
                    .filter(stockItemInDb -> stockItemInDb.getExternalId().equals(stockResponseItem.getId()))
                    .findFirst()
                    .orElseThrow();

            assertThat(stockResponseItem.getId()).isEqualTo(stockReferencedInDb.getExternalId());
            assertThat(stockResponseItem.getName()).isEqualTo(stockReferencedInDb.getName());
            assertThat(stockResponseItem.getPrice()).isEqualTo(stockReferencedInDb.getPrice().getCurrentPrice());
            assertThat(stockResponseItem.getCurrencyValue().toString()).hasToString(stockReferencedInDb.getPrice().getCurrencyValue().toString());
        });
    }

    protected void noStocksInResponse(ResultActions resultActions) throws Exception {
        var response = objectMapper.readValue(getContentString(resultActions), WebStocksResponse.class);
        assertThat(response.getStocks().isEmpty()).isTrue();
    }

    private String getContentString(ResultActions resultActions) throws UnsupportedEncodingException {
        return resultActions.andReturn()
                .getResponse()
                .getContentAsString();
    }
}
