package com.sakurapuare.boatmanagement.pojo.vo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.sql.Timestamp;

@Data
@ApiModel("订单表")
public class BaseBoatOrdersVO {
    private Long id;

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

    @ApiModelProperty("订单类型")
    private String type;


    private Timestamp createdAt;

    private Timestamp updatedAt;
}

