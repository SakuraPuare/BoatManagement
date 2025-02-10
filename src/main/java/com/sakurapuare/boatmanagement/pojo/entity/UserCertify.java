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
 * 用户实名认证表 实体类。
 *
 * @author sakurapuare
 * @since 2025-02-10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("用户实名认证表")
@Table("user_certify")
public class UserCertify extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("")
    private Long id;

    /**
     * 关联用户
     */
    @ApiModelProperty("关联用户")
    private Long userId;

    /**
     * 真实姓名
     */
    @ApiModelProperty("真实姓名")
    private String realName;

    /**
     * 身份证号
     */
    @ApiModelProperty("身份证号")
    private String idCard;

    /**
     * 是否通过
     */
    @ApiModelProperty("是否通过")
    private Boolean isApproved;

}
