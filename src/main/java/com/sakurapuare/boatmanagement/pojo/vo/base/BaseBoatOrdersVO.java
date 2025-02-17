package com.sakurapuare.boatmanagement.pojo.vo.base;

import com.sakurapuare.boatmanagement.pojo.vo.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@ApiModel("船舶订单表")
@EqualsAndHashCode(callSuper = true)
public class BaseBoatOrdersVO extends BaseOrderVO {
    private Long id;

    @ApiModelProperty("请求ID_serverside")
    private Long requestId;

    @ApiModelProperty("指定船只")
    private Long boatId;
}

