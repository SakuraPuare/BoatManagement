package com.sakurapuare.boatmanagement.pojo.vo.base;

import com.sakurapuare.boatmanagement.pojo.vo.BaseOrderVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseBoatOrdersVO extends BaseOrderVO {

    private Long id;

    @ApiModelProperty("请求 ID_serverside")
    private Long requestId;

    @ApiModelProperty("指定船只")
    private Long boatId;

}
