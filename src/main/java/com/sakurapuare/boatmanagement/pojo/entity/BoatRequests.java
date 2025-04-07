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
 * 船只请求表 实体类。
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("船只请求表")
@Table("boat_requests")
public class BoatRequests extends BaseEntity implements Serializable {

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
     * 订单ID_serverside
     */
    @ApiModelProperty("订单ID_serverside")
    private Long orderId;

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
    private LocalDateTime startTime;

    /**
     * 租用结束时间
     */
    @ApiModelProperty("租用结束时间")
    private LocalDateTime endTime;

    /**
     * 订单类型
     */
    @ApiModelProperty("订单类型")
    private String type;

    /**
     * 订单状态
     */
    @ApiModelProperty("订单状态")
    private String status;

}
