package com.felixklemke.stocks.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "stocks")
public class Stock extends AbstractVersionedEntity {

    @NotEmpty
    private String name;

    @Column(unique = true)
    @NotNull
    private UUID externalId;

    @Min(0)
    @NotNull
    private Long currentPrice;
}
