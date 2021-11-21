package com.felixklemke.stocks.components;

import com.felixklemke.stocks.model.Stock;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface StockRepository extends CrudRepository<Stock, Long> {

    Optional<Stock> findByExternalId(UUID externalId);
}
