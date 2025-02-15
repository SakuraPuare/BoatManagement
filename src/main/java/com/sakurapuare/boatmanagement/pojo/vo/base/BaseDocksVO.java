package com.sakurapuare.boatmanagement.pojo.vo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("码头表")
public class BaseDocksVO {
    private Long id;

    @ApiModelProperty("码头名称")
    private String name;

    @ApiModelProperty("地理位置")
    private byte[] location;

    @ApiModelProperty("详细地址")
    private String address;

    @ApiModelProperty("联系电话")
    private String contactPhone;
}

