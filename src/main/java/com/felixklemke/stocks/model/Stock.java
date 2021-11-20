package com.felixklemke.stocks.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("stock")
public class Stock implements Serializable {

    @Id
    private Long id;
    @Indexed
    private UUID externalId;
    @NotBlank
    private String name;
    @Min(0)
    private Long currentPrice;
    @NotBlank
    private String lastModifiedBy;
}
