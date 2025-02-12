package com.sakurapuare.boatmanagement.pojo.vo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("第三方登录表")
public class BaseSocialAuthVO {
    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("")
    private Long accountId;

    @ApiModelProperty("第三方平台")
    private String platform;

    @ApiModelProperty("第三方唯一ID")
    private String openId;

    @ApiModelProperty("跨平台统一ID")
    private String unionId;
}

