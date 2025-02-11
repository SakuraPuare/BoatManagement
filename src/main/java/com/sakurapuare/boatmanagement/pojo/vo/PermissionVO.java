package com.sakurapuare.boatmanagement.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("权限表")
public class PermissionVO {
    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("权限名称")
    private String name;

    @ApiModelProperty("权限代码")
    private String code;

    @ApiModelProperty("权限描述")
    private String description;
}

