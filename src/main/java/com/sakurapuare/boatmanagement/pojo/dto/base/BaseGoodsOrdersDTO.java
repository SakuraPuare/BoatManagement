package com.sakurapuare.boatmanagement.pojo.dto.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Map;
@Data
@ApiModel("商品订单表")
public class BaseGoodsOrdersDTO {


    @ApiModelProperty("订单信息：id:数量,id:数量,id:数量")
    private Map<Integer, Double> orderInfo;
}

