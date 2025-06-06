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
 * 系统日志表_ndto_nvo 实体类。
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统日志表_ndto_nvo")
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
     * 操作人
     */
    @ApiModelProperty("操作人")
    private Long operatorId;

}
