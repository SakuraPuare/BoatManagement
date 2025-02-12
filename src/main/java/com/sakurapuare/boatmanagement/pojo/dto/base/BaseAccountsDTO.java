package com.sakurapuare.boatmanagement.pojo.dto.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("基础账号表")
public class BaseAccountsDTO {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("角色MASK")
    private Integer role;

    @ApiModelProperty("是否激活")
    private Boolean isActive;

    @ApiModelProperty("是否锁定")
    private Boolean isBlocked;
}

