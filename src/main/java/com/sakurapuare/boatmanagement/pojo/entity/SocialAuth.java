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
 * 第三方登录表 实体类。
 *
 * @author sakurapuare
 * @since 2025-02-10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("第三方登录表")
@Table("social_auth")
public class SocialAuth extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("")
    private Long accountId;

    /**
     * 第三方平台
     */
    @ApiModelProperty("第三方平台")
    private String platform;

    /**
     * 第三方唯一ID
     */
    @ApiModelProperty("第三方唯一ID")
    private String openId;

    /**
     * 跨平台统一ID
     */
    @ApiModelProperty("跨平台统一ID")
    private String unionId;

}
