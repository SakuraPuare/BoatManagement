package com.sakurapuare.boatmanagement.pojo.dto.base;

import com.sakurapuare.boatmanagement.pojo.dto.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@ApiModel("船只表")
@EqualsAndHashCode(callSuper = true)
public class BaseBoatsDTO extends BaseEntityDTO {

    @ApiModelProperty("船只名称")
    private String name;

    @ApiModelProperty("船只类型")
    private Long typeId;

    @ApiModelProperty("所属码头")
    private Long dockId;



    @ApiModelProperty("是否启用")
    private Boolean isEnabled;
}

