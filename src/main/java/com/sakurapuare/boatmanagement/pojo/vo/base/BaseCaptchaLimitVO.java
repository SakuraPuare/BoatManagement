package com.sakurapuare.boatmanagement.pojo.vo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.sql.Timestamp;

@Data
@ApiModel("验证码防刷记录")
public class BaseCaptchaLimitVO {
    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("")
    private String target;

    @ApiModelProperty("")
    private String ip;

    @ApiModelProperty("请求次数")
    private Long count;

    @ApiModelProperty("最后请求时间")
    private Timestamp lastRequest;

    @ApiModelProperty("")
    private Boolean isBlocked;
}

