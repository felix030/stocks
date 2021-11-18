package com.felixklemke.stocks;

import com.felixklemke.stocks.model.Stock;
import com.felixklemke.stocks.model.StocksRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
class StocksRepoTest {

    @Autowired
    private StocksRepository stocksRepository;

    @Test
    void saveTest() {

        Stock stock = new Stock();
        stock.setName("Felix");
        stock.setCurrentPrice(1L);
        stock.setExternalId(UUID.randomUUID());

        stocksRepository.save(stock);

        List<Stock> all = stocksRepository.findAll();
    }
}
