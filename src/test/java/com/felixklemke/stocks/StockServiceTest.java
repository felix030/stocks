package com.felixklemke.stocks;

import com.felixklemke.stocks.components.StockService;
import com.felixklemke.stocks.model.Stock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = TestRedisConfiguration.class)
@ActiveProfiles("test")
class StockServiceTest {

    @Autowired
    StockService stockService;

    @Test
    void givenInvalidStockWhenSavedThenThrowException() {
        var stock = Stock.builder()
                .name("")
                .externalId(null)
                .currentPrice(-121312L)
                .build();

        Stock save = stockService.saveStock(stock);
        assertThat(save.getName()).isEqualTo(stock.getName());
    }
}
