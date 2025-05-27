package com.sakurapuare.boatmanagement.pojo.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

import java.io.Serializable;
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
 * 用户优惠券表 实体类。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("用户优惠券表")
@Table("user_coupons")
public class UserCoupons extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("")
    private Long id;

    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private Long userId;

    /**
     * 优惠券ID
     */
    @ApiModelProperty("优惠券ID")
    private Long couponId;

    /**
     * 使用订单ID
     */
    @ApiModelProperty("使用订单ID")
    private Long orderId;

    /**
     * 使用状态
     */
    @ApiModelProperty("使用状态")
    private String status;

    /**
     * 使用时间
     */
    @ApiModelProperty("使用时间")
    private LocalDateTime usedAt;

} 