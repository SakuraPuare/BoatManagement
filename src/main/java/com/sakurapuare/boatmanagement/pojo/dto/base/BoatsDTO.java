package com.sakurapuare.boatmanagement.pojo.dto.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
@Data
@ApiModel("船只表")
public class BoatsDTO {
    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("船只名称")
    private String name;

    @ApiModelProperty("船只类型")
    private Long typeId;

    @ApiModelProperty("船主ID")
    private Long vendorId;

    @ApiModelProperty("所属单位")
    private Long unitId;

    @ApiModelProperty("船身长度（米）")
    private BigDecimal length;

    @ApiModelProperty("船身宽度（米）")
    private BigDecimal width;

    @ApiModelProperty("最大载重（吨）")
    private BigDecimal maxLoad;

    @ApiModelProperty("最大续航（公里）")
    private BigDecimal maxEndurance;
}

