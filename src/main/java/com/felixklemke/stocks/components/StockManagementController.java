package com.felixklemke.stocks.components;

import com.felixklemke.stocks.api.ExternalApi;
import com.felixklemke.stocks.model.Stock;
import com.felixklemke.stocks.model.WebStocksResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("stocks-mgmt")
@RequiredArgsConstructor
public class StockManagementController implements ExternalApi {

    private final StockService stockService;
    private final StockMapper stockMapper;

    @Override
    public ResponseEntity<WebStocksResponse> getAllViewableStocks() {
        List<Stock> stocks = stockService.fetchAllStocks();
        return ResponseEntity.ok(new WebStocksResponse().stocks(stockMapper.map(stocks)));
    }
}
