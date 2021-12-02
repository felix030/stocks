package com.felixklemke.stocks.components;

import com.felixklemke.stocks.model.CurrencyValue;

public record StockCreationRequest(String name, Long price, CurrencyValue currencyValue) {
}
