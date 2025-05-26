package com.sakurapuare.boatmanagement.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseRoleDTO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseRoleVO;
import com.sakurapuare.boatmanagement.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/role")
@Tag(name = "AdminRole", description = "角色管理模块")
@RequiredArgsConstructor
public class AdminRoleController {

    private final RoleService roleService;

    @PostMapping("/list")
    @Operation(summary = "获取角色列表")
    public Response<List<BaseRoleVO>> adminGetRoleList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseRoleDTO filter) {
        return Response.success("获取角色列表成功", 
                roleService.adminGetRoleList(search, sort, startDateTime, endDateTime, filter));
    }

    @PostMapping("/page")
    @Operation(summary = "分页获取角色列表")
    public Response<Page<BaseRoleVO>> adminGetRolePage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseRoleDTO filter) {
        return Response.success("获取角色列表分页成功", 
                roleService.adminGetRolePage(pageNum, pageSize, search, sort, startDateTime, endDateTime, filter));
    }

    @GetMapping("/ids")
    @Operation(summary = "根据 ID 获取角色列表")
    public Response<List<BaseRoleVO>> adminGetRoleByIds(@RequestParam String ids) {
        return Response.success("根据 ID 获取角色列表成功", roleService.adminGetRoleByIds(ids));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取角色详情")
    public Response<BaseRoleVO> adminGetRole(@PathVariable Long id) {
        return Response.success("获取角色详情成功", roleService.adminGetRoleByIds(id.toString()).get(0));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新角色")
    public Response<BaseRoleVO> adminUpdateRole(@PathVariable Long id, @RequestBody BaseRoleDTO baseRoleDTO) {
        return Response.success("更新角色成功", roleService.adminUpdateRole(id, baseRoleDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除角色")
    public Response<String> adminDeleteRole(@PathVariable Long id) {
        roleService.adminDeleteRole(id);
        return Response.success("删除角色成功");
    }

    @PostMapping("/")
    @Operation(summary = "创建角色")
    public Response<BaseRoleVO> adminCreateRole(@RequestBody BaseRoleDTO baseRoleDTO) {
        return Response.success("创建角色成功", roleService.adminCreateRole(baseRoleDTO));
    }

    @PostMapping("/assign")
    @Operation(summary = "为用户分配角色")
    public Response<String> adminAssignRole(
            @RequestParam Long userId,
            @RequestParam Long roleId,
            @RequestParam(required = false) Long unitId) {
        boolean success = roleService.assignRole(userId, roleId, unitId);
        return success ? Response.success("分配角色成功") : Response.error("分配角色失败");
    }

    @PostMapping("/revoke")
    @Operation(summary = "移除用户角色")
    public Response<String> adminRevokeRole(
            @RequestParam Long userId,
            @RequestParam Long roleId,
            @RequestParam(required = false) Long unitId) {
        boolean success = roleService.revokeRole(userId, roleId, unitId);
        return success ? Response.success("移除角色成功") : Response.error("移除角色失败");
    }
} 