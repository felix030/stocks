package com.felixklemke.stocks.components;

import com.felixklemke.stocks.model.Stock;
import com.felixklemke.stocks.model.WebStock;
import com.felixklemke.stocks.model.WebStockRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StockMapper {

    List<WebStock> map(List<Stock> stocks);

    @Mapping(target = "id", source = "externalId")
    @Mapping(target = "price", source = "price.currentPrice")
    @Mapping(target = "currencyValue", source = "price.currencyValue")
    WebStock map(Stock stock);


    StockCreationRequest map(WebStockRequestBody stockRequestBody);
}
