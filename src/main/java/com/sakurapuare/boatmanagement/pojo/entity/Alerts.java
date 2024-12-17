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
 * 告警表 实体类。
 *
 * @author sakurapuare
 * @since 2024-12-17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("告警表")
@Table("alerts")
public class Alerts extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 告警ID
     */
    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("告警ID")
    private Long alertId;

    /**
     * 船只ID
     */
    @ApiModelProperty("船只ID")
    private Long boatId;

    /**
     * 告警类型
     */
    @ApiModelProperty("告警类型")
    private String alertType;

    /**
     * 告警时间
     */
    @ApiModelProperty("告警时间")
    private Timestamp alertTime;

    /**
     * 告警描述
     */
    @ApiModelProperty("告警描述")
    private String description;

    /**
     * 告警状态
     */
    @ApiModelProperty("告警状态")
    private Integer status;

}
