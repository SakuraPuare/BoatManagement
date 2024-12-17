package com.sakurapuare.boatmanagement.pojo.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 支付记录表 实体类。
 *
 * @author sakurapuare
 * @since 2024-12-17
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

    /**
     * 支付ID
     */
    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("支付ID")
    private Long paymentId;

    /**
     * 订单ID
     */
    @ApiModelProperty("订单ID")
    private Long orderId;

    /**
     * 支付时间
     */
    @ApiModelProperty("支付时间")
    private Timestamp paymentTime;

    /**
     * 支付金额
     */
    @ApiModelProperty("支付金额")
    private BigDecimal amount;

    /**
     * 支付方式
     */
    @ApiModelProperty("支付方式")
    private Integer paymentMethod;

}
