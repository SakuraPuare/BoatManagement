package com.sakurapuare.boatmanagement.pojo.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 船票表 实体类。
 *
 * @author sakurapuare
 * @since 2024-12-17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("船票表")
@Table("tickets")
public class Tickets extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 船票ID
     */
    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("船票ID")
    private Long ticketId;

    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private Long userId;

    /**
     * 船只ID
     */
    @ApiModelProperty("船只ID")
    private Long boatId;

    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    private Timestamp startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    private Timestamp endTime;

    /**
     * 出发码头ID
     */
    @ApiModelProperty("出发码头ID")
    private Long departureDockId;

    /**
     * 目的码头ID
     */
    @ApiModelProperty("目的码头ID")
    private Long destinationDockId;

    /**
     * 票价
     */
    @ApiModelProperty("票价")
    private BigDecimal price;

    /**
     * 剩余票数
     */
    @ApiModelProperty("剩余票数")
    private Long remainingTickets;

    /**
     * 状态(0-禁用 1-启用)
     */
    @ApiModelProperty("状态(0-禁用 1-启用)")
    private Integer status;

}
