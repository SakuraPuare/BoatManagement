package com.sakurapuare.boatmanagement.pojo.dto.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("角色权限关联表")
public class BaseRolePermissionDTO {
    @ApiModelProperty("")
    private Long roleId;

    @ApiModelProperty("")
    private Long permissionId;
}

