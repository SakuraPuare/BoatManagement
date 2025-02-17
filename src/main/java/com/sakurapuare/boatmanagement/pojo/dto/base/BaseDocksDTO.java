package com.sakurapuare.boatmanagement.pojo.dto.base;

import com.sakurapuare.boatmanagement.pojo.dto.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@ApiModel("码头表")
@EqualsAndHashCode(callSuper = true)
public class BaseDocksDTO extends BaseEntityDTO {

    @ApiModelProperty("码头名称")
    private String name;

    @ApiModelProperty("地理位置")
    private List<Double> location;

    @ApiModelProperty("详细地址")
    private String address;

    @ApiModelProperty("联系电话")
    private String contactPhone;

    @ApiModelProperty("是否启用")
    private Boolean isEnabled;
}

