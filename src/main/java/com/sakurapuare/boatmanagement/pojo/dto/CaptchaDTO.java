package com.sakurapuare.boatmanagement.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;


@Data
@ApiModel("验证码表")
public class CaptchaDTO {
    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("接收对象")
    private String target;

    @ApiModelProperty("验证码")
    private String code;

    @ApiModelProperty("使用状态")
    private String status;

    @ApiModelProperty("过期时间")
    private Timestamp expireAt;

    @ApiModelProperty("请求IP")
    private String clientIp;
}

