package com.sakurapuare.boatmanagement.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("微信登录请求")
public class WxLoginDTO {
    
    @ApiModelProperty("微信登录code")
    private String code;
    
    @ApiModelProperty("用户信息")
    private WxUserInfo userInfo;
} 