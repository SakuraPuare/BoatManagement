package com.sakurapuare.boatmanagement.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("角色权限关联表")
public class RolePermissionDTO {
    @ApiModelProperty("")
    private Long roleId;

    @ApiModelProperty("")
    private Long permissionId;
}

