package com.sakurapuare.boatmanagement.pojo.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.sakurapuare.boatmanagement.pojo.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;

import java.io.Serial;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * 订单表_ndto_nvo 实体类。
 *
 * @author sakurapuare
 * @since 2025-02-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("订单表_ndto_nvo")
@Table("orders")
public class Orders extends BaseEntity implements Serializable {

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
     * 订单状态_serverside
     */
    @ApiModelProperty("订单状态_serverside")
    private String status;

}
