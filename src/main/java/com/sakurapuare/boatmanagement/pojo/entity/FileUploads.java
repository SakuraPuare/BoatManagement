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
 * 文件上传管理表 实体类。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("文件上传管理表")
@Table("file_uploads")
public class FileUploads extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("")
    private Long id;

    /**
     * 原始文件名
     */
    @ApiModelProperty("原始文件名")
    private String originalName;

    /**
     * 存储文件名
     */
    @ApiModelProperty("存储文件名")
    private String storedName;

    /**
     * 文件路径
     */
    @ApiModelProperty("文件路径")
    private String filePath;

    /**
     * 文件大小（字节）
     */
    @ApiModelProperty("文件大小（字节）")
    private Long fileSize;

    /**
     * 文件类型
     */
    @ApiModelProperty("文件类型")
    private String fileType;

    /**
     * MIME类型
     */
    @ApiModelProperty("MIME类型")
    private String mimeType;

    /**
     * 上传者ID
     */
    @ApiModelProperty("上传者ID")
    private Long uploaderId;

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

} 