package com.felixklemke.stocks;


import com.felixklemke.stocks.components.StockRepository;
import com.felixklemke.stocks.model.Stock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataRedisTest
class StockRepositoryTest {

    @Autowired
    StockRepository repository;

    @Test
    void givenAValidStockWhenSavedThenRetrievableById() {
        var stock = Stock.builder()
                .name("TestStockName")
                .externalId(UUID.randomUUID())
                .currentPrice(1L)
                .build();

        Stock save = repository.save(stock);
        assertThat(save.getName()).isEqualTo(stock.getName());
        assertThat(save.getCurrentPrice()).isEqualTo(stock.getName());
        assertThat(save.getExternalId()).isEqualTo(stock.getExternalId());
    }
}
