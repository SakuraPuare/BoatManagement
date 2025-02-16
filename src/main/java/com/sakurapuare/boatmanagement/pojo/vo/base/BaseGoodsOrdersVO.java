package com.sakurapuare.boatmanagement.pojo.vo.base;

import com.sakurapuare.boatmanagement.pojo.vo.BaseEntityVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;



@Data
@ApiModel("商品订单表")
@EqualsAndHashCode(callSuper = true)
public class BaseGoodsOrdersVO extends BaseEntityVO {
    private Long id;

    @ApiModelProperty("订单信息：id:数量,id:数量,id:数量")
    private String orderInfo;
}

