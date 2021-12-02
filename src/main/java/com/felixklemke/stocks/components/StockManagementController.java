package com.felixklemke.stocks.components;


import com.felixklemke.stocks.api.ExternalApi;
import com.felixklemke.stocks.components.shared.NotFoundException;
import com.felixklemke.stocks.model.Stock;
import com.felixklemke.stocks.model.WebStock;
import com.felixklemke.stocks.model.WebStockRequestBody;
import com.felixklemke.stocks.model.WebStocksResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("stocks-mgmt")
@RequiredArgsConstructor
@Validated
public class StockManagementController implements ExternalApi {

    private final StockService service;
    private final StockMapper mapper;

    @Override
    public ResponseEntity<WebStocksResponse> allViewableStocks() {
        List<Stock> stocks = service.fetchAllStocks();
        return ResponseEntity.ok(new WebStocksResponse().stocks(mapper.map(stocks)));
    }

    @Override
    public ResponseEntity<WebStock> createStock(@Valid WebStockRequestBody stockRequestBody) {
        Stock newStock = service.createNewStock(mapper.map(stockRequestBody));
        return ResponseEntity.ok(mapper.map(newStock));
    }

    @Override
    public ResponseEntity<WebStock> fetchStock(@Valid UUID stockId) {
        return ResponseEntity.ok(service.findByExternalId(stockId).map(mapper::map)
                .orElseThrow(() -> new NotFoundException(String.format("Stock with id %s was not found.", stockId))));
    }
}
