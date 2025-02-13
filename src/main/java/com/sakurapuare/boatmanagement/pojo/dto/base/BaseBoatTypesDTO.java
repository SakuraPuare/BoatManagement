package com.sakurapuare.boatmanagement.pojo.dto.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
@Data
@ApiModel("船只类型表")
public class BaseBoatTypesDTO {

    @ApiModelProperty("类型名称")
    private String typeName;

    @ApiModelProperty("类型描述")
    private String description;

    @ApiModelProperty("船身长度（米）")
    private BigDecimal length;

    @ApiModelProperty("船身宽度（米）")
    private BigDecimal width;

    @ApiModelProperty("核载人数（人）")
    private Long grossNumber;

    @ApiModelProperty("最大载重（吨）")
    private BigDecimal maxLoad;

    @ApiModelProperty("最大航速（公里/小时）")
    private BigDecimal maxSpeed;

    @ApiModelProperty("最大续航（公里）")
    private BigDecimal maxEndurance;


}

