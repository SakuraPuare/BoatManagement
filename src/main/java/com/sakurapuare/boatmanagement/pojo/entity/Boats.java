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
 * @since 2025-02-16
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
     * 所属码头
     */
    @ApiModelProperty("所属码头")
    private Long dockId;

    /**
     * 船主ID_serverside
     */
    @ApiModelProperty("船主ID_serverside")
    private Long vendorId;

    /**
     * 所属单位_serverside
     */
    @ApiModelProperty("所属单位_serverside")
    private Long unitId;

    /**
     * 是否启用
     */
    @ApiModelProperty("是否启用")
    private Boolean isEnabled;

}
