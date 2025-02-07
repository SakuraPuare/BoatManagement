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
import com.sakurapuare.boatmanagement.pojo.entity.RoleInheritance;
import com.sakurapuare.boatmanagement.service.base.BaseRoleInheritanceService;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;

/**
 * 角色继承关系表 控制层。
 *
 * @author sakurapuare
 * @since 2025-02-08
 */
@RestController
@Api("角色继承关系表接口")
@RequestMapping("/roleInheritance")
public class SuperAdminRoleInheritanceController {

    @Autowired
    private BaseRoleInheritanceService baseRoleInheritanceService;

    /**
     * 添加角色继承关系表。
     *
     * @param roleInheritance 角色继承关系表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @ApiOperation("保存角色继承关系表")
    public boolean save(@RequestBody @ApiParam("角色继承关系表") RoleInheritance roleInheritance) {
        return baseRoleInheritanceService.save(roleInheritance);
    }

    /**
     * 根据主键删除角色继承关系表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @ApiOperation("根据主键角色继承关系表")
    public boolean remove(@PathVariable @ApiParam("角色继承关系表主键") Long id) {
        return baseRoleInheritanceService.removeById(id);
    }

    /**
     * 根据主键更新角色继承关系表。
     *
     * @param roleInheritance 角色继承关系表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @ApiOperation("根据主键更新角色继承关系表")
    public boolean update(@RequestBody @ApiParam("角色继承关系表主键") RoleInheritance roleInheritance) {
        return baseRoleInheritanceService.updateById(roleInheritance);
    }

    /**
     * 查询所有角色继承关系表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @ApiOperation("查询所有角色继承关系表")
    public List<RoleInheritance> list() {
        return baseRoleInheritanceService.list();
    }

    /**
     * 根据角色继承关系表主键获取详细信息。
     *
     * @param id 角色继承关系表主键
     * @return 角色继承关系表详情
     */
    @GetMapping("getInfo/{id}")
    @ApiOperation("根据主键获取角色继承关系表")
    public RoleInheritance getInfo(@PathVariable @ApiParam("角色继承关系表主键") Long id) {
        return baseRoleInheritanceService.getById(id);
    }

    /**
     * 分页查询角色继承关系表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @ApiOperation("分页查询角色继承关系表")
    public Page<RoleInheritance> page(@ApiParam("分页信息") Page<RoleInheritance> page) {
        return baseRoleInheritanceService.page(page);
    }

}
