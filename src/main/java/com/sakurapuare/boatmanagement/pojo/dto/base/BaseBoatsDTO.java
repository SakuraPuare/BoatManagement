package com.sakurapuare.boatmanagement.pojo.dto.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;



@Data
@ApiModel("船只表")
public class BaseBoatsDTO {

    @ApiModelProperty("船只名称")
    private String name;

    @ApiModelProperty("船只类型")
    private Long typeId;

    @ApiModelProperty("船只类型")
    private Long boatTypeId;

    @ApiModelProperty("所属码头")
    private Long dockId;



    @ApiModelProperty("是否启用")
    private Boolean isEnabled;
}

