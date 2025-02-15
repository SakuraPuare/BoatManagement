package com.sakurapuare.boatmanagement.pojo.vo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.sql.Timestamp;

@Data
@ApiModel("商品订单表")
public class BaseGoodsOrdersVO {
    private Long id;

    @ApiModelProperty("订单信息：id:数量,id:数量,id:数量")
    private String orderInfo;


    private Timestamp createdAt;

    private Timestamp updatedAt;
}

