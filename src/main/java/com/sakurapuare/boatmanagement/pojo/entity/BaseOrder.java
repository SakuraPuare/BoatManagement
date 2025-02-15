package com.sakurapuare.boatmanagement.pojo.entity;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BaseOrder extends BaseEntity {

    private Long orderId;

    private Long userId;

    private String status;

    private BigDecimal price;

    private BigDecimal discount;
}
