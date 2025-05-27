package com.sakurapuare.boatmanagement.pojo.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serial;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * 支付记录表 实体类。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("支付记录表")
@Table("payments")
public class Payments extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("")
    private Long id;

    /**
     * 订单ID
     */
    @ApiModelProperty("订单ID")
    private Long orderId;

    /**
     * 订单类型
     */
    @ApiModelProperty("订单类型")
    private String orderType;

    /**
     * 支付用户
     */
    @ApiModelProperty("支付用户")
    private Long userId;

    /**
     * 支付金额
     */
    @ApiModelProperty("支付金额")
    private BigDecimal amount;

    /**
     * 支付方式
     */
    @ApiModelProperty("支付方式")
    private String paymentMethod;

    /**
     * 第三方交易号
     */
    @ApiModelProperty("第三方交易号")
    private String transactionId;

    /**
     * 支付状态
     */
    @ApiModelProperty("支付状态")
    private String status;

    /**
     * 支付时间
     */
    @ApiModelProperty("支付时间")
    private LocalDateTime paidAt;

} 