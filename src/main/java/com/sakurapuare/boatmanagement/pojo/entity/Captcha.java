package com.sakurapuare.boatmanagement.pojo.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

import java.io.Serializable;
import java.time.LocalDateTime;

import java.io.Serial;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * 验证码表_ndto_nvo 实体类。
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("验证码表_ndto_nvo")
@Table("captcha")
public class Captcha extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("")
    private Long id;

    /**
     * 接收对象
     */
    @ApiModelProperty("接收对象")
    private String target;

    /**
     * 验证码
     */
    @ApiModelProperty("验证码")
    private String code;

    /**
     * 使用状态
     */
    @ApiModelProperty("使用状态")
    private String status;

    /**
     * 过期时间
     */
    @ApiModelProperty("过期时间")
    private LocalDateTime expireAt;

    /**
     * 请求 IP
     */
    @ApiModelProperty("请求 IP")
    private String clientIp;

}
