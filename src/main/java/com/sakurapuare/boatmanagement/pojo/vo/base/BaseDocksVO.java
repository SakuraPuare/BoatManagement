package com.sakurapuare.boatmanagement.pojo.vo.base;

import com.sakurapuare.boatmanagement.pojo.vo.BaseEntityVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;



@Data
@ApiModel("码头表")
@EqualsAndHashCode(callSuper = true)
public class BaseDocksVO extends BaseEntityVO {
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

