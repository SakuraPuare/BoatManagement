package com.sakurapuare.boatmanagement.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Column;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BaseOrder {

    private Long userId;

    private String status;

    private BigDecimal price;

    private BigDecimal discount;

    @Column(isLogicDelete = true)
    private Boolean isDeleted;

    @Column(onInsertValue = "now()")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Column(onUpdateValue = "now()")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
