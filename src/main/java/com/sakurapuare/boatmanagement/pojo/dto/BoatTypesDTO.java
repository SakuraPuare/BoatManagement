package com.sakurapuare.boatmanagement.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("船只类型表")
public class BoatTypesDTO {
    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("类型名称")
    private String typeName;

    @ApiModelProperty("类型描述")
    private String description;
}

