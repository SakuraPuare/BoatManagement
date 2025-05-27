package com.sakurapuare.boatmanagement.pojo.vo.base;

import com.sakurapuare.boatmanagement.pojo.vo.BaseEntityVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseNotificationsVO extends BaseEntityVO {

    private Long id;

    @ApiModelProperty("接收用户")
    private Long userId;

    @ApiModelProperty("通知标题")
    private String title;

    @ApiModelProperty("通知内容")
    private String content;

    @ApiModelProperty("通知类型")
    private String type;

    @ApiModelProperty("业务类型")
    private String businessType;

    @ApiModelProperty("关联业务ID")
    private Long businessId;

    @ApiModelProperty("是否已读")
    private Boolean isRead;

} 