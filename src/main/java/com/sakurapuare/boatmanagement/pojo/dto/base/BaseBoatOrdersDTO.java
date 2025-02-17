package com.sakurapuare.boatmanagement.pojo.dto.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
@ApiModel("船舶订单表")
public class BaseBoatOrdersDTO {


    @ApiModelProperty("指定船只")
    private Long boatId;
}

