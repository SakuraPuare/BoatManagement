package com.sakurapuare.boatmanagement.pojo.vo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.sql.Timestamp;
import java.math.BigDecimal;
@Data
@ApiModel("订单表")
public class BaseOrdersVO {
    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("下单用户")
    private Long userId;

    @ApiModelProperty("指定船只")
    private Long boatId;

    @ApiModelProperty("起始码头")
    private Long startDockId;

    @ApiModelProperty("目的码头")
    private Long endDockId;

    @ApiModelProperty("租用开始时间")
    private Timestamp startTime;

    @ApiModelProperty("租用结束时间")
    private Timestamp endTime;

    @ApiModelProperty("订单总金额")
    private BigDecimal totalAmount;

    @ApiModelProperty("")
    private String status;

    @ApiModelProperty("订单类型")
    private String type;
}

