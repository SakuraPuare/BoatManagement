package com.sakurapuare.boatmanagement.pojo.dto.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("用户实名认证表")
public class UserCertifyDTO {
    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("关联用户")
    private Long userId;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("身份证号")
    private String idCard;

    @ApiModelProperty("审核状态")
    private String status;
}

