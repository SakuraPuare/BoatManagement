package com.sakurapuare.boatmanagement.pojo.dto.base;

import com.sakurapuare.boatmanagement.pojo.dto.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Map;
@Data
@ApiModel("商品订单表")
@EqualsAndHashCode(callSuper = true)
public class BaseGoodsOrdersDTO extends BaseOrderDTO {


    @ApiModelProperty("订单信息：id:数量,id:数量,id:数量")
    private Map<Long, Double> orderInfo;
}

