package com.sakurapuare.boatmanagement.pojo.entity;

import com.mybatisflex.annotation.Id;
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
 * 用户角色关联表 实体类。
 *
 * @author sakurapuare
 * @since 2025-02-12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("用户角色关联表")
@Table("user_role")
public class UserRole extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @ApiModelProperty("")
    private Long userId;

    @Id
    @ApiModelProperty("")
    private Long roleId;

    /**
     * 所属单位
     */
    @Id
    @ApiModelProperty("所属单位")
    private Long unitId;

}
