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

/**
 * 船只类型表 实体类。
 *
 * @author sakurapuare
 * @since 2024-12-17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("船只类型表")
@Table("boat_types")
public class BoatTypes extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 船只类型ID
     */
    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("船只类型ID")
    private Long boatTypeId;

    /**
     * 类型名称
     */
    @ApiModelProperty("类型名称")
    private String typeName;

    /**
     * 类型代码
     */
    @ApiModelProperty("类型代码")
    private String typeCode;

    /**
     * 最大载量(吨)
     */
    @ApiModelProperty("最大载量(吨)")
    private BigDecimal maxCapacity;

    /**
     * 最高速度(节)
     */
    @ApiModelProperty("最高速度(节)")
    private BigDecimal maxSpeed;

    /**
     * 燃料类型
     */
    @ApiModelProperty("燃料类型")
    private String fuelType;

    /**
     * 状态(0-禁用 1-启用)
     */
    @ApiModelProperty("状态(0-禁用 1-启用)")
    private Integer status;

}
