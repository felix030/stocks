package com.felixklemke.stocks.components;

import com.felixklemke.stocks.model.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Validated
@RequiredArgsConstructor
@Transactional
public class StockService {

    private final StockRepository repository;

    public Stock saveStock(@Valid Stock stock) {
        System.err.println();
        return repository.save(stock);
    }
}
