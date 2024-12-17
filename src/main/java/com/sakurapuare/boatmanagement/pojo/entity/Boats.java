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
 * 船只表 实体类。
 *
 * @author sakurapuare
 * @since 2024-12-17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("船只表")
@Table("boats")
public class Boats extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 船只ID
     */
    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("船只ID")
    private Long boatId;

    /**
     * 船只名称
     */
    @ApiModelProperty("船只名称")
    private String boatName;

    /**
     * 船只类型ID
     */
    @ApiModelProperty("船只类型ID")
    private Long boatTypeId;

    /**
     * 注册编号
     */
    @ApiModelProperty("注册编号")
    private String registrationNumber;

    /**
     * 建造年份
     */
    @ApiModelProperty("建造年份")
    private Integer buildYear;

    /**
     * 上次维护时间
     */
    @ApiModelProperty("上次维护时间")
    private Timestamp lastMaintenance;

    /**
     * 下次维护时间
     */
    @ApiModelProperty("下次维护时间")
    private Timestamp nextMaintenance;

    /**
     * 当前码头ID
     */
    @ApiModelProperty("当前码头ID")
    private Long currentDockId;

    /**
     * 船只状态
     */
    @ApiModelProperty("船只状态")
    private Integer status;

}
