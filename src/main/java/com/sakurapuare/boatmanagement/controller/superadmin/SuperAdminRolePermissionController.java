package com.sakurapuare.boatmanagement.controller.superadmin;

import com.mybatisflex.core.paginate.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.sakurapuare.boatmanagement.pojo.entity.RolePermission;
import com.sakurapuare.boatmanagement.service.base.BaseRolePermissionService;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;

/**
 * 角色权限关联表 控制层。
 *
 * @author sakurapuare
 * @since 2025-02-08
 */
@RestController
@Api("角色权限关联表接口")
@RequestMapping("/rolePermission")
public class SuperAdminRolePermissionController {

    @Autowired
    private BaseRolePermissionService baseRolePermissionService;

    /**
     * 添加角色权限关联表。
     *
     * @param rolePermission 角色权限关联表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @ApiOperation("保存角色权限关联表")
    public boolean save(@RequestBody @ApiParam("角色权限关联表") RolePermission rolePermission) {
        return baseRolePermissionService.save(rolePermission);
    }

    /**
     * 根据主键删除角色权限关联表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @ApiOperation("根据主键角色权限关联表")
    public boolean remove(@PathVariable @ApiParam("角色权限关联表主键") Long id) {
        return baseRolePermissionService.removeById(id);
    }

    /**
     * 根据主键更新角色权限关联表。
     *
     * @param rolePermission 角色权限关联表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @ApiOperation("根据主键更新角色权限关联表")
    public boolean update(@RequestBody @ApiParam("角色权限关联表主键") RolePermission rolePermission) {
        return baseRolePermissionService.updateById(rolePermission);
    }

    /**
     * 查询所有角色权限关联表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @ApiOperation("查询所有角色权限关联表")
    public List<RolePermission> list() {
        return baseRolePermissionService.list();
    }

    /**
     * 根据角色权限关联表主键获取详细信息。
     *
     * @param id 角色权限关联表主键
     * @return 角色权限关联表详情
     */
    @GetMapping("getInfo/{id}")
    @ApiOperation("根据主键获取角色权限关联表")
    public RolePermission getInfo(@PathVariable @ApiParam("角色权限关联表主键") Long id) {
        return baseRolePermissionService.getById(id);
    }

    /**
     * 分页查询角色权限关联表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @ApiOperation("分页查询角色权限关联表")
    public Page<RolePermission> page(@ApiParam("分页信息") Page<RolePermission> page) {
        return baseRolePermissionService.page(page);
    }

}
