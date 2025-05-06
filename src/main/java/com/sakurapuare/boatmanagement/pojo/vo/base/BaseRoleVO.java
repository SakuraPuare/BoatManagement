package com.sakurapuare.boatmanagement.pojo.vo.base;

import com.sakurapuare.boatmanagement.pojo.vo.BaseEntityVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseRoleVO extends BaseEntityVO {

    private Long id;

    @ApiModelProperty("角色名称")
    private String name;

    @ApiModelProperty("角色描述")
    private String description;

}
