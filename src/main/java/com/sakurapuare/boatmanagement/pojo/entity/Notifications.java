package com.sakurapuare.boatmanagement.pojo.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

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
 * 通知表 实体类。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("通知表")
@Table("notifications")
public class Notifications extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("")
    private Long id;

    /**
     * 接收用户
     */
    @ApiModelProperty("接收用户")
    private Long userId;

    /**
     * 通知标题
     */
    @ApiModelProperty("通知标题")
    private String title;

    /**
     * 通知内容
     */
    @ApiModelProperty("通知内容")
    private String content;

    /**
     * 通知类型
     */
    @ApiModelProperty("通知类型")
    private String type;

    /**
     * 业务类型
     */
    @ApiModelProperty("业务类型")
    private String businessType;

    /**
     * 关联业务ID
     */
    @ApiModelProperty("关联业务ID")
    private Long businessId;

    /**
     * 是否已读
     */
    @ApiModelProperty("是否已读")
    private Boolean isRead;

} 