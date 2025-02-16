package com.sakurapuare.boatmanagement.pojo.vo.base;

import com.sakurapuare.boatmanagement.pojo.vo.BaseEntityVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;



@Data
@ApiModel("船只表")
@EqualsAndHashCode(callSuper = true)
public class BaseBoatsVO extends BaseEntityVO {
    private Long id;

    @ApiModelProperty("船只名称")
    private String name;

    @ApiModelProperty("船只类型")
    private Long typeId;

    @ApiModelProperty("船只类型")
    private Long boatTypeId;

    @ApiModelProperty("所属码头")
    private Long dockId;

    @ApiModelProperty("船主ID_serverside")
    private Long vendorId;

    @ApiModelProperty("所属单位_serverside")
    private Long unitId;
}

