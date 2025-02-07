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
import com.sakurapuare.boatmanagement.pojo.entity.Permission;
import com.sakurapuare.boatmanagement.service.base.BasePermissionService;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;

/**
 * 权限表 控制层。
 *
 * @author sakurapuare
 * @since 2025-02-08
 */
@RestController
@Api("权限表接口")
@RequestMapping("/permission")
public class SuperAdminPermissionController {

    @Autowired
    private BasePermissionService basePermissionService;

    /**
     * 添加权限表。
     *
     * @param permission 权限表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @ApiOperation("保存权限表")
    public boolean save(@RequestBody @ApiParam("权限表") Permission permission) {
        return basePermissionService.save(permission);
    }

    /**
     * 根据主键删除权限表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @ApiOperation("根据主键权限表")
    public boolean remove(@PathVariable @ApiParam("权限表主键") Long id) {
        return basePermissionService.removeById(id);
    }

    /**
     * 根据主键更新权限表。
     *
     * @param permission 权限表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @ApiOperation("根据主键更新权限表")
    public boolean update(@RequestBody @ApiParam("权限表主键") Permission permission) {
        return basePermissionService.updateById(permission);
    }

    /**
     * 查询所有权限表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @ApiOperation("查询所有权限表")
    public List<Permission> list() {
        return basePermissionService.list();
    }

    /**
     * 根据权限表主键获取详细信息。
     *
     * @param id 权限表主键
     * @return 权限表详情
     */
    @GetMapping("getInfo/{id}")
    @ApiOperation("根据主键获取权限表")
    public Permission getInfo(@PathVariable @ApiParam("权限表主键") Long id) {
        return basePermissionService.getById(id);
    }

    /**
     * 分页查询权限表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @ApiOperation("分页查询权限表")
    public Page<Permission> page(@ApiParam("分页信息") Page<Permission> page) {
        return basePermissionService.page(page);
    }

}
