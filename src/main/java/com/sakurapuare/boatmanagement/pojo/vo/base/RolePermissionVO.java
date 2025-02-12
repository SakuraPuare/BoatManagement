package com.sakurapuare.boatmanagement.pojo.vo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("角色权限关联表")
public class RolePermissionVO {
    @ApiModelProperty("")
    private Long roleId;

    @ApiModelProperty("")
    private Long permissionId;
}

