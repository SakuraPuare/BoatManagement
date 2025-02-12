package com.sakurapuare.boatmanagement.pojo.dto.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("船只表")
public class BaseBoatsDTO {
    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("船只名称")
    private String name;

    @ApiModelProperty("船只类型")
    private Long typeId;

    @ApiModelProperty("船主ID")
    private Long vendorId;

    @ApiModelProperty("所属单位")
    private Long unitId;

    @ApiModelProperty("船只类型")
    private Long boatTypeId;
}

