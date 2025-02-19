package com.sakurapuare.boatmanagement.pojo.vo.base;

import com.sakurapuare.boatmanagement.pojo.vo.BaseEntityVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseBoatRequestsVO extends BaseEntityVO {

    private Long id;

    @ApiModelProperty("下单用户_serverside")
    private Long userId;

    @ApiModelProperty("订单ID_serverside")
    private Long orderId;

    @ApiModelProperty("起始码头")
    private Long startDockId;

    @ApiModelProperty("目的码头")
    private Long endDockId;

    @ApiModelProperty("租用开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty("租用结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty("订单类型")
    private String type;

    @ApiModelProperty("订单状态")
    private String status;

}
