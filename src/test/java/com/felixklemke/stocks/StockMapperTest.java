package com.felixklemke.stocks;

import com.felixklemke.stocks.components.StockMapper;
import com.felixklemke.stocks.model.Price;
import com.felixklemke.stocks.model.Stock;
import com.felixklemke.stocks.model.WebStock;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class StockMapperTest {

    private StockMapper stockMapper = Mappers.getMapper(StockMapper.class);;


    @Test
    void givenValidStock_whenMappingToWebObject_thenCorrectValuesMapped() {
        Price price = Price.builder()
                .currentPrice(100L)
                .lastUpdatedBy("Tester")
                .build();

        var stockToBeMapped = Stock.builder()
                .name("TestStockName")
                .externalId(UUID.randomUUID())
                .price(price)
                .build();

        WebStock webStock = stockMapper.map(stockToBeMapped);
        assertThat(webStock.getPrice()).isEqualTo(stockToBeMapped.getPrice().getCurrentPrice());
        assertThat(webStock.getName()).isEqualTo(stockToBeMapped.getName());
        assertThat(webStock.getId()).isEqualTo(stockToBeMapped.getExternalId());
    }
}
