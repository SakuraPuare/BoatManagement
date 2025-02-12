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
 * 船只表 实体类。
 *
 * @author sakurapuare
 * @since 2025-02-13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("船只表")
@Table("boats")
public class Boats extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("")
    private Long id;

    /**
     * 船只名称
     */
    @ApiModelProperty("船只名称")
    private String name;

    /**
     * 船只类型
     */
    @ApiModelProperty("船只类型")
    private Long typeId;

    /**
     * 船主ID
     */
    @ApiModelProperty("船主ID")
    private Long vendorId;

    /**
     * 所属单位
     */
    @ApiModelProperty("所属单位")
    private Long unitId;

    /**
     * 船只类型
     */
    @ApiModelProperty("船只类型")
    private Long boatTypeId;

}
