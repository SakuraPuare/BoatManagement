package com.sakurapuare.boatmanagement.pojo.vo.base;

import com.sakurapuare.boatmanagement.pojo.vo.BaseEntityVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;



@Data
@ApiModel("船舶订单表")
@EqualsAndHashCode(callSuper = true)
public class BaseBoatOrdersVO extends BaseEntityVO {
    private Long id;

    @ApiModelProperty("指定船只")
    private Long boatId;

    @ApiModelProperty("请求ID")
    private Long requestId;
}

