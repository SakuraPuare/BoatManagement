package com.sakurapuare.boatmanagement.pojo.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.sakurapuare.boatmanagement.handler.GoodsInfoTypeHandler;
import com.sakurapuare.boatmanagement.pojo.entity.BaseOrder;
import java.io.Serializable;
import java.util.Map;

import java.io.Serial;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * 商品订单表 实体类。
 *
 * @author sakurapuare
 * @since 2025-02-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("商品订单表")
@Table("goods_orders")
public class GoodsOrders extends BaseOrder implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("")
    private Long id;

    /**
     * 商家ID_serverside
     */
    @ApiModelProperty("商家ID_serverside")
    private Long merchantId;

    /**
     * 订单信息：id:数量,id:数量,id:数量
     */
    @Column(typeHandler = GoodsInfoTypeHandler.class)
    @ApiModelProperty("订单信息：id:数量,id:数量,id:数量")
    private Map<Integer, Double> orderInfo;

}
