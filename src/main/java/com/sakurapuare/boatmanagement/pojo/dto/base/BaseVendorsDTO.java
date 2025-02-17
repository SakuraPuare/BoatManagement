package com.sakurapuare.boatmanagement.pojo.dto.base;

import com.sakurapuare.boatmanagement.pojo.dto.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@ApiModel("船主表")
@EqualsAndHashCode(callSuper = true)
public class BaseVendorsDTO extends BaseEntityDTO {

    @ApiModelProperty("关联用户")
    private Long userId;

    @ApiModelProperty("所属单位")
    private Long unitId;

    @ApiModelProperty("审核状态")
    private String status;
}

