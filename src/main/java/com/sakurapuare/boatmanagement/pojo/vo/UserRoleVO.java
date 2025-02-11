package com.sakurapuare.boatmanagement.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("用户角色关联表")
public class UserRoleVO {
    @ApiModelProperty("")
    private Long userId;

    @ApiModelProperty("")
    private Long roleId;

    @ApiModelProperty("所属单位")
    private Long unitId;
}

