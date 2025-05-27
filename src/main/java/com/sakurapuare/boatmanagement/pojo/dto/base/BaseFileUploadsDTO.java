package com.sakurapuare.boatmanagement.pojo.dto.base;

import com.sakurapuare.boatmanagement.pojo.dto.BaseEntityDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseFileUploadsDTO extends BaseEntityDTO {

    @ApiModelProperty("原始文件名")
    private String originalName;

    @ApiModelProperty("存储文件名")
    private String storedName;

    @ApiModelProperty("文件路径")
    private String filePath;

    @ApiModelProperty("文件大小（字节）")
    private Long fileSize;

    @ApiModelProperty("文件类型")
    private String fileType;

    @ApiModelProperty("MIME类型")
    private String mimeType;

    @ApiModelProperty("上传者ID")
    private Long uploaderId;

    @ApiModelProperty("业务类型")
    private String businessType;

    @ApiModelProperty("关联业务ID")
    private Long businessId;

} 