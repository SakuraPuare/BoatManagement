package com.sakurapuare.boatmanagement.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("系统日志表")
public class LogsDTO {
    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("日志类型")
    private String type;

    @ApiModelProperty("日志等级")
    private String level;

    @ApiModelProperty("日志内容")
    private String content;

    @ApiModelProperty("来源模块")
    private String source;

    @ApiModelProperty("操作人")
    private Long operatorId;
}

