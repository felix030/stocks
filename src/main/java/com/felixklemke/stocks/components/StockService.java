package com.felixklemke.stocks.components;

import com.felixklemke.stocks.model.Price;
import com.felixklemke.stocks.model.Stock;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
@Transactional
public class StockService {

    private final StockRepository repository;


    public Stock saveStock(@Valid Stock stock) {
        return repository.save(stock);
    }

    public Optional<Stock> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<Stock> findByExternalId(UUID externalId) {
        return repository.findByExternalId(externalId);
    }

    public List<Stock> fetchAllStocks() {
        return IterableUtils.toList(repository.findAll());
    }

    public Stock createNewStock(StockCreationRequest creationRequest) {
        return saveStock(Stock.builder()
                .name(creationRequest.name())
                .externalId(UUID.randomUUID())
                .price(Price.builder()
                        .currentPrice(creationRequest.price())
                        .currencyValue(creationRequest.currencyValue())
                        .build())
                .build());
    }
}
