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
 * 基础账号表 实体类。
 *
 * @author sakurapuare
 * @since 2025-02-10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("基础账号表")
@Table("accounts")
public class Accounts extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("主键ID")
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String phone;

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String email;

    /**
     * 角色MASK
     */
    @ApiModelProperty("角色MASK")
    private Integer role;

    /**
     * 是否激活
     */
    @ApiModelProperty("是否激活")
    private Boolean isActive;

    /**
     * 是否锁定
     */
    @ApiModelProperty("是否锁定")
    private Boolean isBlocked;

}
