package com.sakurapuare.boatmanagement.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseFileUploadsDTO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseFileUploadsVO;
import com.sakurapuare.boatmanagement.service.FileUploadsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员文件上传管理控制器
 */
@Api(tags = "管理员文件上传管理")
@RestController
@RequestMapping("/admin/file-uploads")
public class AdminFileUploadController {

    @Autowired
    private FileUploadsService fileUploadsService;

    /**
     * 获取文件上传列表
     */
    @ApiOperation("获取文件上传列表")
    @PostMapping("/list")
    public Response<List<BaseFileUploadsVO>> getFileUploadsList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseFileUploadsDTO filter) {
        List<BaseFileUploadsVO> result = fileUploadsService.adminGetFileUploadsList(
                search, sort, startDateTime, endDateTime, filter);
        return Response.success(result);
    }

    /**
     * 分页获取文件上传列表
     */
    @ApiOperation("分页获取文件上传列表")
    @PostMapping("/page")
    public Response<Page<BaseFileUploadsVO>> getFileUploadsPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseFileUploadsDTO filter) {
        Page<BaseFileUploadsVO> result = fileUploadsService.adminGetFileUploadsPage(
                pageNum, pageSize, search, sort, startDateTime, endDateTime, filter);
        return Response.success(result);
    }

    /**
     * 根据ID获取文件上传
     */
    @ApiOperation("根据ID获取文件上传")
    @GetMapping("/ids")
    public Response<List<BaseFileUploadsVO>> getFileUploadsByIds(@RequestParam String ids) {
        List<BaseFileUploadsVO> result = fileUploadsService.adminGetFileUploadsByIds(ids);
        return Response.success(result);
    }

    /**
     * 创建文件上传记录
     */
    @ApiOperation("创建文件上传记录")
    @PostMapping
    public Response<BaseFileUploadsVO> createFileUpload(@RequestBody BaseFileUploadsDTO fileUploadsDTO) {
        BaseFileUploadsVO result = fileUploadsService.adminCreateFileUploads(fileUploadsDTO);
        return Response.success(result);
    }

    /**
     * 更新文件上传记录
     */
    @ApiOperation("更新文件上传记录")
    @PutMapping("/{id}")
    public Response<BaseFileUploadsVO> updateFileUpload(
            @PathVariable Long id,
            @RequestBody BaseFileUploadsDTO fileUploadsDTO) {
        BaseFileUploadsVO result = fileUploadsService.adminUpdateFileUploads(id, fileUploadsDTO);
        return Response.success(result);
    }

    /**
     * 删除文件上传记录
     */
    @ApiOperation("删除文件上传记录")
    @DeleteMapping("/{id}")
    public Response<Void> deleteFileUpload(@PathVariable Long id) {
        fileUploadsService.adminDeleteFileUploads(id);
        return Response.success();
    }
} 