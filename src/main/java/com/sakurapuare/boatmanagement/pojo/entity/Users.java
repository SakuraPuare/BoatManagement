package com.sakurapuare.boatmanagement.pojo.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.sakurapuare.boatmanagement.utils.JWTUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户表 实体类。
 *
 * @author sakurapuare
 * @since 2024-12-17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("用户表")
@Table("users")
public class Users extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("用户ID")
    private Long userId;

    /**
     * 用户UUID
     */
    @ApiModelProperty("用户UUID")
    private String uuid;

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
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String email;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String phone;

    /**
     * 角色权限
     */
    @ApiModelProperty("角色权限")
    private Long role;

    /**
     * 是否激活
     */
    @ApiModelProperty("是否激活")
    private Boolean isActive;

    /**
     * 是否封禁
     */
    @ApiModelProperty("是否封禁")
    private Boolean isBlocked;

    /**
     * 微信openid
     */
    @ApiModelProperty("微信openid")
    private String openid;

    /**
     * 头像URL
     */
    @ApiModelProperty("头像URL")
    private String avatar;

    public String getToken() {
        Map<String, Object> map = new HashMap<>();

        map.put("userId", this.userId);
        map.put("uuid", this.uuid);
        map.put("role", this.role);

        return JWTUtils.generateToken(map);
    }
}
