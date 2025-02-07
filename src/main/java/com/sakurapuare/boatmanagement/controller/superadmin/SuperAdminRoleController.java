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
import com.sakurapuare.boatmanagement.pojo.entity.Role;
import com.sakurapuare.boatmanagement.service.base.BaseRoleService;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;

/**
 * 角色表 控制层。
 *
 * @author sakurapuare
 * @since 2025-02-08
 */
@RestController
@Api("角色表接口")
@RequestMapping("/role")
public class SuperAdminRoleController {

    @Autowired
    private BaseRoleService baseRoleService;

    /**
     * 添加角色表。
     *
     * @param role 角色表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @ApiOperation("保存角色表")
    public boolean save(@RequestBody @ApiParam("角色表") Role role) {
        return baseRoleService.save(role);
    }

    /**
     * 根据主键删除角色表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @ApiOperation("根据主键角色表")
    public boolean remove(@PathVariable @ApiParam("角色表主键") Long id) {
        return baseRoleService.removeById(id);
    }

    /**
     * 根据主键更新角色表。
     *
     * @param role 角色表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @ApiOperation("根据主键更新角色表")
    public boolean update(@RequestBody @ApiParam("角色表主键") Role role) {
        return baseRoleService.updateById(role);
    }

    /**
     * 查询所有角色表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @ApiOperation("查询所有角色表")
    public List<Role> list() {
        return baseRoleService.list();
    }

    /**
     * 根据角色表主键获取详细信息。
     *
     * @param id 角色表主键
     * @return 角色表详情
     */
    @GetMapping("getInfo/{id}")
    @ApiOperation("根据主键获取角色表")
    public Role getInfo(@PathVariable @ApiParam("角色表主键") Long id) {
        return baseRoleService.getById(id);
    }

    /**
     * 分页查询角色表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @ApiOperation("分页查询角色表")
    public Page<Role> page(@ApiParam("分页信息") Page<Role> page) {
        return baseRoleService.page(page);
    }

}
