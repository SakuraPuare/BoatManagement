package com.sakurapuare.boatmanagement.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("认证请求")
public class AuthRequestDTO {

    @ApiModelProperty("用户名/手机号/邮箱")
    private String username;

    @ApiModelProperty("密码/验证码")
    private String password;
}
