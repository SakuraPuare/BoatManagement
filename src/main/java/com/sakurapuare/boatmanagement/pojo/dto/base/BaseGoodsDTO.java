package com.sakurapuare.boatmanagement.pojo.dto.base;

import com.sakurapuare.boatmanagement.pojo.dto.BaseEntityDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseGoodsDTO extends BaseEntityDTO {

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品描述")
    private String description;

    @ApiModelProperty("商品价格")
    private BigDecimal price;

    @ApiModelProperty("商品单位")
    private String unit;

    @ApiModelProperty("库存")
    private Long stock;

    @ApiModelProperty("是否启用")
    private Boolean isEnabled;

}
