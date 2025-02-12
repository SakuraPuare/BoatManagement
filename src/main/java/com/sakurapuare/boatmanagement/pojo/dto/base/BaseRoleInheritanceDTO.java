package com.sakurapuare.boatmanagement.pojo.dto.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("角色继承关系表")
public class BaseRoleInheritanceDTO {
    private Long parentRoleId;

    private Long childRoleId;
}

