package com.sakurapuare.boatmanagement.pojo.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.sakurapuare.boatmanagement.pojo.entity.BaseEntity;
import java.io.Serializable;

import java.io.Serial;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * 单位表 实体类。
 *
 * @author sakurapuare
 * @since 2025-02-17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("单位表")
@Table("units")
public class Units extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("")
    private Long id;

    /**
     * 对外名称
     */
    @ApiModelProperty("对外名称")
    private String name;

    /**
     * 单位名称
     */
    @ApiModelProperty("单位名称")
    private String unitName;

    /**
     * 统一社会信用代码
     */
    @ApiModelProperty("统一社会信用代码")
    private String socialCreditCode;

    /**
     * 法人代表
     */
    @ApiModelProperty("法人代表")
    private String legalPerson;

    /**
     * 单位地址
     */
    @ApiModelProperty("单位地址")
    private String address;

    /**
     * 联系电话
     */
    @ApiModelProperty("联系电话")
    private String contactPhone;

    /**
     * 审核状态
     */
    @ApiModelProperty("审核状态")
    private String status;

    /**
     * 单位管理员
     */
    @ApiModelProperty("单位管理员")
    private Long adminUserId;

    /**
     * 单位类型
     */
    @ApiModelProperty("单位类型")
    private String types;

}
