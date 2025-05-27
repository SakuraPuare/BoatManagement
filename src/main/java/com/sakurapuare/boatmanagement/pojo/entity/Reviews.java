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
 * 评价表 实体类。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("评价表")
@Table("reviews")
public class Reviews extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("")
    private Long id;

    /**
     * 评价用户
     */
    @ApiModelProperty("评价用户")
    private Long userId;

    /**
     * 评价对象类型
     */
    @ApiModelProperty("评价对象类型")
    private String targetType;

    /**
     * 评价对象ID
     */
    @ApiModelProperty("评价对象ID")
    private Long targetId;

    /**
     * 关联订单ID
     */
    @ApiModelProperty("关联订单ID")
    private Long orderId;

    /**
     * 评分(1-5)
     */
    @ApiModelProperty("评分(1-5)")
    private Integer rating;

    /**
     * 评价内容
     */
    @ApiModelProperty("评价内容")
    private String content;

    /**
     * 评价图片
     */
    @ApiModelProperty("评价图片")
    private String images;

    /**
     * 是否匿名
     */
    @ApiModelProperty("是否匿名")
    private Boolean isAnonymous;

} 