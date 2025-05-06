package com.sakurapuare.boatmanagement.pojo.dto.base;

import com.sakurapuare.boatmanagement.pojo.dto.BaseEntityDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseRoleDTO extends BaseEntityDTO {

    @ApiModelProperty("角色名称")
    private String name;

    @ApiModelProperty("角色描述")
    private String description;

}
