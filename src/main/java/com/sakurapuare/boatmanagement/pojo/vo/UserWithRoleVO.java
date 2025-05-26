package com.sakurapuare.boatmanagement.pojo.vo;

import com.sakurapuare.boatmanagement.pojo.vo.base.BaseAccountsVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserWithRoleVO extends BaseAccountsVO {

    @ApiModelProperty("用户角色掩码")
    private Integer role;

} 