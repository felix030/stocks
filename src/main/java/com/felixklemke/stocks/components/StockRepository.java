package com.felixklemke.stocks.components;

import com.felixklemke.stocks.model.Stock;
import org.springframework.data.repository.CrudRepository;

public interface StockRepository extends CrudRepository<Stock, Long> {
}
