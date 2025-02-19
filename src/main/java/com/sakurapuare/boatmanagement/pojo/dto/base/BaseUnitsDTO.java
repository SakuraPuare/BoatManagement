package com.sakurapuare.boatmanagement.pojo.dto.base;

import com.sakurapuare.boatmanagement.pojo.dto.BaseEntityDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseUnitsDTO extends BaseEntityDTO {

    @ApiModelProperty("对外名称")
    private String name;

    @ApiModelProperty("单位名称")
    private String unitName;

    @ApiModelProperty("统一社会信用代码")
    private String socialCreditCode;

    @ApiModelProperty("法人代表")
    private String legalPerson;

    @ApiModelProperty("单位地址")
    private String address;

    @ApiModelProperty("联系电话")
    private String contactPhone;

    @ApiModelProperty("审核状态")
    private String status;

    @ApiModelProperty("单位管理员")
    private Long adminUserId;

    @ApiModelProperty("单位类型")
    private String types;

}
