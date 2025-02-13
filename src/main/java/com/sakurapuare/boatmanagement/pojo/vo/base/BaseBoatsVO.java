package com.sakurapuare.boatmanagement.pojo.vo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("船只表")
public class BaseBoatsVO {
    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("船只名称")
    private String name;

    @ApiModelProperty("船只类型")
    private Long typeId;

    @ApiModelProperty("船只类型")
    private Long boatTypeId;

    @ApiModelProperty("所属码头")
    private Long dockId;

    @ApiModelProperty("船主ID_serverside")
    private Long vendorId;

    @ApiModelProperty("所属单位_serverside")
    private Long unitId;
}

