package com.sakurapuare.boatmanagement.pojo.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

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
 * 船只类型表 实体类。
 *
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

    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("")
    private Long id;

    /**
     * 类型名称
     */
    @ApiModelProperty("类型名称")
    private String typeName;

    /**
     * 类型描述
     */
    @ApiModelProperty("类型描述")
    private String description;

    /**
     * 船身长度（米）
     */
    @ApiModelProperty("船身长度（米）")
    private BigDecimal length;

    /**
     * 船身宽度（米）
     */
    @ApiModelProperty("船身宽度（米）")
    private BigDecimal width;

    /**
     * 核载人数（人）
     */
    @ApiModelProperty("核载人数（人）")
    private Long grossNumber;

    /**
     * 最大载重（吨）
     */
    @ApiModelProperty("最大载重（吨）")
    private BigDecimal maxLoad;

    /**
     * 最大航速（公里/小时）
     */
    @ApiModelProperty("最大航速（公里/小时）")
    private BigDecimal maxSpeed;

    /**
     * 最大续航（公里）
     */
    @ApiModelProperty("最大续航（公里）")
    private BigDecimal maxEndurance;

    /**
     * 租金（元/小时）
     */
    @ApiModelProperty("租金（元/小时）")
    private BigDecimal price;

    /**
     * 创建者_serverside
     */
    @ApiModelProperty("创建者_serverside")
    private Long vendorId;

    /**
     * 创建单位_serverside
     */
    @ApiModelProperty("创建单位_serverside")
    private Long unitId;

    /**
     * 是否启用
     */
    @ApiModelProperty("是否启用")
    private Boolean isEnabled;

}
