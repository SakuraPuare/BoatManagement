package com.sakurapuare.boatmanagement.pojo.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 验证码表 实体类。
 *
 * @author sakurapuare
 * @since 2024-12-17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("验证码表")
@Table("codes")
public class Codes extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 验证码ID
     */
    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("验证码ID")
    private Long id;

    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private Long userId;

    /**
     * 验证码
     */
    @ApiModelProperty("验证码")
    private String code;

    /**
     * 请求时间
     */
    @ApiModelProperty("请求时间")
    private Timestamp requestTime;

    /**
     * 过期时间
     */
    @ApiModelProperty("过期时间")
    private Timestamp expirationTime;

}
