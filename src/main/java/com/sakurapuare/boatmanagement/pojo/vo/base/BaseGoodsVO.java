package com.sakurapuare.boatmanagement.pojo.vo.base;

import com.sakurapuare.boatmanagement.pojo.vo.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
@Data
@ApiModel("商品表")
@EqualsAndHashCode(callSuper = true)
public class BaseGoodsVO extends BaseEntityVO {
    private Long id;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品描述")
    private String description;

    @ApiModelProperty("商品价格")
    private BigDecimal price;

    @ApiModelProperty("商品单位")
    private String unit;

    @ApiModelProperty("库存_serverside")
    private Long stock;

    @ApiModelProperty("销量_serverside")
    private Long sales;

    @ApiModelProperty("创建商家_serverside")
    private Long merchantId;

    @ApiModelProperty("创建单位_serverside")
    private Long unitId;
}

