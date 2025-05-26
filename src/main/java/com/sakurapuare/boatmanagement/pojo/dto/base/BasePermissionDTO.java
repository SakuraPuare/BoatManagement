package com.sakurapuare.boatmanagement.pojo.dto.base;

import com.sakurapuare.boatmanagement.pojo.dto.BaseEntityDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BasePermissionDTO extends BaseEntityDTO {

    @ApiModelProperty("权限名称")
    private String name;

    @ApiModelProperty("权限代码")
    private String code;

    @ApiModelProperty("权限描述")
    private String description;

} 