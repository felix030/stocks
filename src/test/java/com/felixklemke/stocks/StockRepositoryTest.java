package com.felixklemke.stocks;


import com.felixklemke.stocks.components.StockRepository;
import com.felixklemke.stocks.model.Stock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class StockRepositoryTest extends AbstractIntegrationTest {

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
        assertThat(save.getCurrentPrice()).isEqualTo(stock.getCurrentPrice());
        assertThat(save.getExternalId()).isEqualTo(stock.getExternalId());
    }
}
