package com.felixklemke.stocks;

import com.felixklemke.stocks.components.StockRepository;
import com.felixklemke.stocks.model.Price;
import com.felixklemke.stocks.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class StocksGiven {

    private final Random random = new Random();
    private AtomicLong stockNameCounter = new AtomicLong(1);

    @Autowired
    private StockRepository stockRepository;

    protected List<Stock> validListOfStocks() {
        return List.of(validStockFromDb(), validStockFromDb(), validStockFromDb());
    }

    protected Stock validStockFromDb() {
        return stockRepository.save(validStock());
    }

    protected Stock validStock() {
        Price price = Price.builder()
                .currentPrice(Math.abs(random.nextLong(1000000)))
                .lastUpdatedBy("Tester")
                .build();

        var stockToBeSaved = Stock.builder()
                .name("TestStockName" + stockNameCounter.incrementAndGet())
                .externalId(UUID.randomUUID())
                .price(price)
                .build();

        return stockToBeSaved;
    }

    public void dropDbContent() {
        stockRepository.deleteAll();
    }
}
