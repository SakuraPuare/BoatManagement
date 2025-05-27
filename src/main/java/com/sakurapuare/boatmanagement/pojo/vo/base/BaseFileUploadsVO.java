package com.sakurapuare.boatmanagement.pojo.vo.base;

import com.sakurapuare.boatmanagement.pojo.vo.BaseEntityVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseFileUploadsVO extends BaseEntityVO {

    private Long id;

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