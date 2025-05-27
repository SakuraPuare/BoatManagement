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
 * 优惠券表 实体类。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("优惠券表")
@Table("coupons")
public class Coupons extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("")
    private Long id;

    /**
     * 优惠券名称
     */
    @ApiModelProperty("优惠券名称")
    private String name;

    /**
     * 优惠券代码
     */
    @ApiModelProperty("优惠券代码")
    private String code;

    /**
     * 优惠类型：固定金额/百分比
     */
    @ApiModelProperty("优惠类型：固定金额/百分比")
    private String type;

    /**
     * 优惠值
     */
    @ApiModelProperty("优惠值")
    private BigDecimal value;

    /**
     * 最小使用金额
     */
    @ApiModelProperty("最小使用金额")
    private BigDecimal minAmount;

    /**
     * 最大优惠金额
     */
    @ApiModelProperty("最大优惠金额")
    private BigDecimal maxDiscount;

    /**
     * 发放总数
     */
    @ApiModelProperty("发放总数")
    private Integer totalCount;

    /**
     * 已使用数量
     */
    @ApiModelProperty("已使用数量")
    private Integer usedCount;

    /**
     * 有效期开始
     */
    @ApiModelProperty("有效期开始")
    private LocalDateTime startTime;

    /**
     * 有效期结束
     */
    @ApiModelProperty("有效期结束")
    private LocalDateTime endTime;

    /**
     * 适用类型
     */
    @ApiModelProperty("适用类型")
    private String applicableType;

    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
    private Long creatorId;

    /**
     * 是否启用
     */
    @ApiModelProperty("是否启用")
    private Boolean isEnabled;

} 