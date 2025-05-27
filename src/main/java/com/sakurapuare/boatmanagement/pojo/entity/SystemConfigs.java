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
 * 系统配置表 实体类。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统配置表")
@Table("system_configs")
public class SystemConfigs extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("")
    private Long id;

    /**
     * 配置键
     */
    @ApiModelProperty("配置键")
    private String configKey;

    /**
     * 配置值
     */
    @ApiModelProperty("配置值")
    private String configValue;

    /**
     * 配置类型
     */
    @ApiModelProperty("配置类型")
    private String configType;

    /**
     * 配置描述
     */
    @ApiModelProperty("配置描述")
    private String description;

    /**
     * 是否公开配置
     */
    @ApiModelProperty("是否公开配置")
    private Boolean isPublic;

} 