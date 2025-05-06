package com.sakurapuare.boatmanagement.pojo.vo.base;

import com.sakurapuare.boatmanagement.pojo.vo.BaseOrderVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseGoodsOrdersVO extends BaseOrderVO {

    private Long id;

    @ApiModelProperty("商家 ID_serverside")
    private Long merchantId;

    @ApiModelProperty("订单信息：id:数量，id:数量，id:数量")
    private Map<Long, Double> orderInfo;

}
