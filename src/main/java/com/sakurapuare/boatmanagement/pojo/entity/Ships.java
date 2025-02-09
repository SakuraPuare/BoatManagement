package com.sakurapuare.boatmanagement.pojo.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.sakurapuare.boatmanagement.pojo.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;

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
 * @since 2025-02-10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("船只表")
@Table("ships")
public class Ships extends BaseEntity implements Serializable {

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
    private Long ownerId;

    /**
     * 所属单位
     */
    @ApiModelProperty("所属单位")
    private Long unitId;

    /**
     * 船身长度（米）
     */
    @ApiModelProperty("船身长度（米）")
    private BigDecimal length;

    /**
     * 船身宽度（米）
     */
    @ApiModelProperty("船身宽度（米）")
    private BigDecimal width;

    /**
     * 最大载重（吨）
     */
    @ApiModelProperty("最大载重（吨）")
    private BigDecimal maxLoad;

    /**
     * 最大续航（公里）
     */
    @ApiModelProperty("最大续航（公里）")
    private BigDecimal maxEndurance;

}
