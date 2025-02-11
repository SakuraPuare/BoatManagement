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
 * 系统日志表 实体类。
 *
 * @author sakurapuare
 * @since 2025-02-12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统日志表")
@Table("logs")
public class Logs extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("")
    private Long id;

    /**
     * 日志类型
     */
    @ApiModelProperty("日志类型")
    private String type;

    /**
     * 日志等级
     */
    @ApiModelProperty("日志等级")
    private String level;

    /**
     * 日志内容
     */
    @ApiModelProperty("日志内容")
    private String content;

    /**
     * 来源模块
     */
    @ApiModelProperty("来源模块")
    private String source;

    /**
     * 操作人
     */
    @ApiModelProperty("操作人")
    private Long operatorId;

}
