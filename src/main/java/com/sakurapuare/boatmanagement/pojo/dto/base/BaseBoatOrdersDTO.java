package com.sakurapuare.boatmanagement.pojo.dto.base;

import com.sakurapuare.boatmanagement.pojo.dto.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@ApiModel("船舶订单表")
@EqualsAndHashCode(callSuper = true)
public class BaseBoatOrdersDTO extends BaseOrderDTO {


    @ApiModelProperty("指定船只")
    private Long boatId;
}

