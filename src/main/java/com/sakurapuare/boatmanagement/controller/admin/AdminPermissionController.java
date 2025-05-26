package com.sakurapuare.boatmanagement.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.base.BasePermissionDTO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BasePermissionVO;
import com.sakurapuare.boatmanagement.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/permission")
@Tag(name = "AdminPermission", description = "权限管理模块")
@RequiredArgsConstructor
public class AdminPermissionController {

    private final PermissionService permissionService;

    @PostMapping("/list")
    @Operation(summary = "获取权限列表")
    public Response<List<BasePermissionVO>> adminGetPermissionList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BasePermissionDTO filter) {
        return Response.success("获取权限列表成功", 
                permissionService.adminGetPermissionList(search, sort, startDateTime, endDateTime, filter));
    }

    @PostMapping("/page")
    @Operation(summary = "分页获取权限列表")
    public Response<Page<BasePermissionVO>> adminGetPermissionPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BasePermissionDTO filter) {
        return Response.success("获取权限列表分页成功", 
                permissionService.adminGetPermissionPage(pageNum, pageSize, search, sort, startDateTime, endDateTime, filter));
    }

    @GetMapping("/ids")
    @Operation(summary = "根据 ID 获取权限列表")
    public Response<List<BasePermissionVO>> adminGetPermissionByIds(@RequestParam String ids) {
        return Response.success("根据 ID 获取权限列表成功", permissionService.adminGetPermissionByIds(ids));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取权限详情")
    public Response<BasePermissionVO> adminGetPermission(@PathVariable Long id) {
        return Response.success("获取权限详情成功", permissionService.adminGetPermissionByIds(id.toString()).get(0));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新权限")
    public Response<BasePermissionVO> adminUpdatePermission(@PathVariable Long id, @RequestBody BasePermissionDTO basePermissionDTO) {
        return Response.success("更新权限成功", permissionService.adminUpdatePermission(id, basePermissionDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除权限")
    public Response<String> adminDeletePermission(@PathVariable Long id) {
        permissionService.adminDeletePermission(id);
        return Response.success("删除权限成功");
    }

    @PostMapping("/")
    @Operation(summary = "创建权限")
    public Response<BasePermissionVO> adminCreatePermission(@RequestBody BasePermissionDTO basePermissionDTO) {
        return Response.success("创建权限成功", permissionService.adminCreatePermission(basePermissionDTO));
    }
} 