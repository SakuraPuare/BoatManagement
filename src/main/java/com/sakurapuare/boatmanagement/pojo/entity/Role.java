package com.sakurapuare.boatmanagement.pojo.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
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
 * 角色表 实体类。
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("角色表")
@Table("role")
public class Role extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("")
    private Long id;

    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    private String name;

    /**
     * 角色描述
     */
    @ApiModelProperty("角色描述")
    private String description;

}
