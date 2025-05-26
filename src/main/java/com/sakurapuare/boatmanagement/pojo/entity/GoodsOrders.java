package com.sakurapuare.boatmanagement.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.sakurapuare.boatmanagement.handler.GoodsInfoTypeHandler;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import java.io.Serial;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品订单表 实体类。
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("商品订单表")
@Table("goods_orders")
public class GoodsOrders implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("")
    private Long id;

    /**
     * 下单用户_serverside
     */
    @ApiModelProperty("下单用户_serverside")
    private Long userId;

    /**
     * 商家 ID_serverside
     */
    @ApiModelProperty("商家 ID_serverside")
    private Long merchantId;

    /**
     * 订单信息：id:数量，id:数量，id:数量
     */
    @Column(typeHandler = GoodsInfoTypeHandler.class)
    @ApiModelProperty("订单信息：id:数量，id:数量，id:数量")
    private Map<Long, Double> orderInfo;

    /**
     * 折扣_serverside
     */
    @ApiModelProperty("折扣_serverside")
    private BigDecimal discount;

    /**
     * 订单总金额_serverside
     */
    @ApiModelProperty("订单总金额_serverside")
    private BigDecimal price;

    /**
     * 订单状态
     */
    @ApiModelProperty("订单状态")
    private String status;

    @Column(isLogicDelete = true)
    private Boolean isDeleted;

    @Column(onInsertValue = "now()")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Column(onUpdateValue = "now()")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
