package com.sakurapuare.boatmanagement.pojo.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 码头表 实体类。
 *
 * @author sakurapuare
 * @since 2024-12-17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("码头表")
@Table("docks")
public class Docks extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 码头ID
     */
    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("码头ID")
    private Long dockId;

    /**
     * 码头名称
     */
    @ApiModelProperty("码头名称")
    private String dockName;

    /**
     * 码头位置
     */
    @ApiModelProperty("码头位置")
    private String dockLocation;

    /**
     * 状态(0-禁用 1-启用)
     */
    @ApiModelProperty("状态(0-禁用 1-启用)")
    private Integer status;

}
