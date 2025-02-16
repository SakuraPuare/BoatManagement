package com.sakurapuare.boatmanagement.pojo.dto.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;



@Data
@ApiModel("商品订单表")
public class BaseGoodsOrdersDTO {

    @ApiModelProperty("商家ID")
    private Long merchantId;

    @ApiModelProperty("订单信息：id:数量,id:数量,id:数量")
    private String orderInfo;
}

