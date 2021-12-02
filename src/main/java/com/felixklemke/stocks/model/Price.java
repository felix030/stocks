package com.felixklemke.stocks.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Price implements Serializable {

    @Min(0)
    @NotNull
    private Long currentPrice;
    @NotEmpty
    private String lastUpdatedBy;
    @NotNull
    @Builder.Default
    private CurrencyValue currencyValue = CurrencyValue.EURO_CENT;
}
