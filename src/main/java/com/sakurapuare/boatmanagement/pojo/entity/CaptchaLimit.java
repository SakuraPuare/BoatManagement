package com.sakurapuare.boatmanagement.pojo.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.sakurapuare.boatmanagement.pojo.entity.BaseEntity;
import java.io.Serializable;
import java.sql.Timestamp;

import java.io.Serial;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * 验证码防刷记录 实体类。
 *
 * @author sakurapuare
 * @since 2025-02-12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("验证码防刷记录")
@Table("captcha_limit")
public class CaptchaLimit extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("")
    private String target;

    @ApiModelProperty("")
    private String ip;

    /**
     * 请求次数
     */
    @ApiModelProperty("请求次数")
    private Long count;

    /**
     * 最后请求时间
     */
    @ApiModelProperty("最后请求时间")
    private Timestamp lastRequest;

    /**
     * 是否锁定
     */
    @ApiModelProperty("是否锁定")
    private Boolean isBlocked;

}
