package com.sakurapuare.boatmanagement.pojo.vo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("角色继承关系表")
public class BaseRoleInheritanceVO {
    @ApiModelProperty("")
    private Long parentRoleId;

    @ApiModelProperty("")
    private Long childRoleId;
}

