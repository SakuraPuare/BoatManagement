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
import com.sakurapuare.boatmanagement.pojo.entity.UserRole;
import com.sakurapuare.boatmanagement.service.base.BaseUserRoleService;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;

/**
 * 用户角色关联表 控制层。
 *
 * @author sakurapuare
 * @since 2025-02-08
 */
@RestController
@Api("用户角色关联表接口")
@RequestMapping("/userRole")
public class SuperAdminUserRoleController {

    @Autowired
    private BaseUserRoleService baseUserRoleService;

    /**
     * 添加用户角色关联表。
     *
     * @param userRole 用户角色关联表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @ApiOperation("保存用户角色关联表")
    public boolean save(@RequestBody @ApiParam("用户角色关联表") UserRole userRole) {
        return baseUserRoleService.save(userRole);
    }

    /**
     * 根据主键删除用户角色关联表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @ApiOperation("根据主键用户角色关联表")
    public boolean remove(@PathVariable @ApiParam("用户角色关联表主键") BigInteger id) {
        return baseUserRoleService.removeById(id);
    }

    /**
     * 根据主键更新用户角色关联表。
     *
     * @param userRole 用户角色关联表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @ApiOperation("根据主键更新用户角色关联表")
    public boolean update(@RequestBody @ApiParam("用户角色关联表主键") UserRole userRole) {
        return baseUserRoleService.updateById(userRole);
    }

    /**
     * 查询所有用户角色关联表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @ApiOperation("查询所有用户角色关联表")
    public List<UserRole> list() {
        return baseUserRoleService.list();
    }

    /**
     * 根据用户角色关联表主键获取详细信息。
     *
     * @param id 用户角色关联表主键
     * @return 用户角色关联表详情
     */
    @GetMapping("getInfo/{id}")
    @ApiOperation("根据主键获取用户角色关联表")
    public UserRole getInfo(@PathVariable @ApiParam("用户角色关联表主键") BigInteger id) {
        return baseUserRoleService.getById(id);
    }

    /**
     * 分页查询用户角色关联表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @ApiOperation("分页查询用户角色关联表")
    public Page<UserRole> page(@ApiParam("分页信息") Page<UserRole> page) {
        return baseUserRoleService.page(page);
    }

}
