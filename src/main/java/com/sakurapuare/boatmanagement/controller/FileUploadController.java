package com.sakurapuare.boatmanagement.controller;

import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseFileUploadsDTO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseFileUploadsVO;
import com.sakurapuare.boatmanagement.service.FileUploadsService;
import com.sakurapuare.boatmanagement.utils.FileUploadUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件上传控制器
 */
@Api(tags = "文件上传管理")
@RestController
@RequestMapping("/files")
public class FileUploadController {

    @Autowired
    private FileUploadsService fileUploadsService;

    /**
     * 单文件上传
     */
    @ApiOperation("单文件上传")
    @PostMapping("/upload")
    public Response<BaseFileUploadsVO> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("uploaderId") Long uploaderId,
            @RequestParam("businessType") String businessType,
            @RequestParam(value = "businessId", required = false) Long businessId) {
        
        // 验证文件
        if (file.isEmpty()) {
            throw new RuntimeException("文件不能为空");
        }

        if (!FileUploadUtils.isValidFileSize(file)) {
            throw new RuntimeException("文件大小超过限制");
        }

        if (!FileUploadUtils.isValidFileForBusinessType(file, businessType)) {
            throw new RuntimeException("文件类型不符合业务要求");
        }

        try {
            // 生成文件名和路径
            String originalName = file.getOriginalFilename();
            String storedName = FileUploadUtils.generateUniqueFileName(originalName);
            String filePath = FileUploadUtils.generateFilePath(businessType, storedName);

            // 保存文件
            FileUploadUtils.saveFile(file, filePath);

            // 创建文件记录
            BaseFileUploadsDTO fileUploadDTO = new BaseFileUploadsDTO();
            fileUploadDTO.setOriginalName(originalName);
            fileUploadDTO.setStoredName(storedName);
            fileUploadDTO.setFilePath(filePath);
            fileUploadDTO.setFileSize(file.getSize());
            fileUploadDTO.setFileType(FileUploadUtils.getFileExtension(originalName));
            fileUploadDTO.setMimeType(file.getContentType());
            fileUploadDTO.setUploaderId(uploaderId);
            fileUploadDTO.setBusinessType(businessType);
            fileUploadDTO.setBusinessId(businessId);

            BaseFileUploadsVO result = fileUploadsService.adminCreateFileUploads(fileUploadDTO);
            return Response.success(result);

        } catch (IOException e) {
            throw new RuntimeException("文件保存失败: " + e.getMessage());
        }
    }

    /**
     * 批量文件上传
     */
    @ApiOperation("批量文件上传")
    @PostMapping("/upload/batch")
    public Response<List<BaseFileUploadsVO>> uploadFiles(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("uploaderId") Long uploaderId,
            @RequestParam("businessType") String businessType,
            @RequestParam(value = "businessId", required = false) Long businessId) {
        
        if (files == null || files.length == 0) {
            throw new RuntimeException("文件不能为空");
        }

        List<BaseFileUploadsVO> results = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }

            if (!FileUploadUtils.isValidFileSize(file)) {
                throw new RuntimeException("文件 " + file.getOriginalFilename() + " 大小超过限制");
            }

            if (!FileUploadUtils.isValidFileForBusinessType(file, businessType)) {
                throw new RuntimeException("文件 " + file.getOriginalFilename() + " 类型不符合业务要求");
            }

            try {
                // 生成文件名和路径
                String originalName = file.getOriginalFilename();
                String storedName = FileUploadUtils.generateUniqueFileName(originalName);
                String filePath = FileUploadUtils.generateFilePath(businessType, storedName);

                // 保存文件
                FileUploadUtils.saveFile(file, filePath);

                // 创建文件记录
                BaseFileUploadsDTO fileUploadDTO = new BaseFileUploadsDTO();
                fileUploadDTO.setOriginalName(originalName);
                fileUploadDTO.setStoredName(storedName);
                fileUploadDTO.setFilePath(filePath);
                fileUploadDTO.setFileSize(file.getSize());
                fileUploadDTO.setFileType(FileUploadUtils.getFileExtension(originalName));
                fileUploadDTO.setMimeType(file.getContentType());
                fileUploadDTO.setUploaderId(uploaderId);
                fileUploadDTO.setBusinessType(businessType);
                fileUploadDTO.setBusinessId(businessId);

                BaseFileUploadsVO result = fileUploadsService.adminCreateFileUploads(fileUploadDTO);
                results.add(result);

            } catch (IOException e) {
                throw new RuntimeException("文件 " + file.getOriginalFilename() + " 保存失败: " + e.getMessage());
            }
        }

        return Response.success(results);
    }

    /**
     * 文件下载
     */
    @ApiOperation("文件下载")
    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId, HttpServletRequest request) {
        BaseFileUploadsVO fileInfo = fileUploadsService.getFileUploadsInfo(fileId);
        
        try {
            Path filePath = Paths.get(fileInfo.getFilePath()).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                throw new RuntimeException("文件不存在");
            }

            // 确定文件的内容类型
            String contentType = null;
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {
                // 忽略异常
            }

            // 如果无法确定内容类型，则使用默认值
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, 
                            "attachment; filename=\"" + fileInfo.getOriginalName() + "\"")
                    .body(resource);

        } catch (MalformedURLException ex) {
            throw new RuntimeException("文件路径错误");
        }
    }

    /**
     * 文件预览
     */
    @ApiOperation("文件预览")
    @GetMapping("/preview/{fileId}")
    public ResponseEntity<Resource> previewFile(@PathVariable Long fileId, HttpServletRequest request) {
        BaseFileUploadsVO fileInfo = fileUploadsService.getFileUploadsInfo(fileId);
        
        try {
            Path filePath = Paths.get(fileInfo.getFilePath()).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                throw new RuntimeException("文件不存在");
            }

            // 确定文件的内容类型
            String contentType = fileInfo.getMimeType();
            if (contentType == null) {
                try {
                    contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
                } catch (IOException ex) {
                    contentType = "application/octet-stream";
                }
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileInfo.getOriginalName() + "\"")
                    .body(resource);

        } catch (MalformedURLException ex) {
            throw new RuntimeException("文件路径错误");
        }
    }

    /**
     * 删除文件
     */
    @ApiOperation("删除文件")
    @DeleteMapping("/{fileId}")
    public Response<Void> deleteFile(@PathVariable Long fileId) {
        BaseFileUploadsVO fileInfo = fileUploadsService.getFileUploadsInfo(fileId);
        
        // 删除物理文件
        FileUploadUtils.deleteFile(fileInfo.getFilePath());
        
        // 删除数据库记录
        fileUploadsService.adminDeleteFileUploads(fileId);
        
        return Response.success();
    }

    /**
     * 根据业务获取文件列表
     */
    @ApiOperation("根据业务获取文件列表")
    @GetMapping("/business")
    public Response<List<BaseFileUploadsVO>> getFilesByBusiness(
            @RequestParam String businessType,
            @RequestParam Long businessId) {
        List<BaseFileUploadsVO> result = fileUploadsService.getFilesByBusiness(businessType, businessId);
        return Response.success(result);
    }

    /**
     * 获取用户文件列表
     */
    @ApiOperation("获取用户文件列表")
    @PostMapping("/user/list")
    public Response<List<BaseFileUploadsVO>> getUserFilesList(
            @RequestParam Long userId,
            @RequestParam(required = false) String businessType,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseFileUploadsDTO filter) {
        List<BaseFileUploadsVO> result = fileUploadsService.userGetFileUploadsList(
                userId, businessType, search, sort, startDateTime, endDateTime, filter);
        return Response.success(result);
    }
} 