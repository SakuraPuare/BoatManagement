package com.sakurapuare.boatmanagement.pojo.entity;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseOrder {

    private Long orderId;

    private Long userId;

    private String status;

    private BigDecimal price;

    private BigDecimal discount;
}
