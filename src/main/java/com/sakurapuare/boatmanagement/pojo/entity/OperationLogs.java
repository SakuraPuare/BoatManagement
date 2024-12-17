package com.sakurapuare.boatmanagement.pojo.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 操作日志表 实体类。
 *
 * @author sakurapuare
 * @since 2024-12-17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("操作日志表")
@Table("operation_logs")
public class OperationLogs extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 日志ID
     */
    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("日志ID")
    private Long logId;

    /**
     * 表名
     */
    @ApiModelProperty("表名")
    private String tableName;

    /**
     * 操作类型(INSERT/UPDATE/DELETE)
     */
    @ApiModelProperty("操作类型(INSERT/UPDATE/DELETE)")
    private String operation;

    /**
     * 记录ID
     */
    @ApiModelProperty("记录ID")
    private Long recordId;

    /**
     * 操作者ID
     */
    @ApiModelProperty("操作者ID")
    private Long operatorId;

    /**
     * 原数据
     */
    @ApiModelProperty("原数据")
    private String oldData;

    /**
     * 新数据
     */
    @ApiModelProperty("新数据")
    private String newData;

    /**
     * IP地址
     */
    @ApiModelProperty("IP地址")
    private String ipAddress;

}
