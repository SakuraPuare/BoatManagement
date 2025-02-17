package com.sakurapuare.boatmanagement.pojo.vo.base;

import com.sakurapuare.boatmanagement.pojo.vo.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
@Data
@ApiModel("船只类型表")
@EqualsAndHashCode(callSuper = true)
public class BaseBoatTypesVO extends BaseEntityVO {
    private Long id;

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

    @ApiModelProperty("租金（元/小时）")
    private BigDecimal price;

    @ApiModelProperty("创建者_serverside")
    private Long vendorId;

    @ApiModelProperty("创建单位_serverside")
    private Long unitId;

    @ApiModelProperty("是否启用")
    private Boolean isEnabled;
}

