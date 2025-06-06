package com.sakurapuare.boatmanagement.pojo.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;

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
 * 用户角色关联表_ndto_nvo 实体类。
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("用户角色关联表_ndto_nvo")
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
