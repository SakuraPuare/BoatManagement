package com.sakurapuare.boatmanagement.pojo.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.sakurapuare.boatmanagement.pojo.entity.BaseOrder;
import java.io.Serializable;
import java.sql.Timestamp;

import java.io.Serial;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * 订单表 实体类。
 *
 * @author sakurapuare
 * @since 2025-02-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("订单表")
@Table("boat_orders")
public class BoatOrders extends BaseOrder implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("")
    private Long id;

    /**
     * 指定船只
     */
    @ApiModelProperty("指定船只")
    private Long boatId;

    /**
     * 起始码头
     */
    @ApiModelProperty("起始码头")
    private Long startDockId;

    /**
     * 目的码头
     */
    @ApiModelProperty("目的码头")
    private Long endDockId;

    /**
     * 租用开始时间
     */
    @ApiModelProperty("租用开始时间")
    private Timestamp startTime;

    /**
     * 租用结束时间
     */
    @ApiModelProperty("租用结束时间")
    private Timestamp endTime;

    /**
     * 订单类型
     */
    @ApiModelProperty("订单类型")
    private String type;

    @ApiModelProperty("")
    private Boolean isDeleted;

    @ApiModelProperty("")
    private Timestamp createdAt;

    @ApiModelProperty("")
    private Timestamp updatedAt;

}
