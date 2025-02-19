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
 * 商品表 实体类。
 *
 * @author sakurapuare
 * @since 2025-02-20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("商品表")
@Table("goods")
public class Goods extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("")
    private Long id;

    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    private String name;

    /**
     * 商品描述
     */
    @ApiModelProperty("商品描述")
    private String description;

    /**
     * 商品价格
     */
    @ApiModelProperty("商品价格")
    private BigDecimal price;

    /**
     * 商品单位
     */
    @ApiModelProperty("商品单位")
    private String unit;

    /**
     * 库存
     */
    @ApiModelProperty("库存")
    private Long stock;

    /**
     * 销量_serverside
     */
    @ApiModelProperty("销量_serverside")
    private Long sales;

    /**
     * 创建商家_serverside
     */
    @ApiModelProperty("创建商家_serverside")
    private Long merchantId;

    /**
     * 创建单位_serverside
     */
    @ApiModelProperty("创建单位_serverside")
    private Long unitId;

}
