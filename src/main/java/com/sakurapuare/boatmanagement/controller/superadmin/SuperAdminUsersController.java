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
import com.sakurapuare.boatmanagement.pojo.entity.Users;
import com.sakurapuare.boatmanagement.service.UsersService;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;

/**
 * 用户表 控制层。
 *
 * @author sakurapuare
 * @since 2024-12-26
 */
@RestController
@Api("用户表接口")
@RequestMapping("/superadmin/users")
public class SuperAdminUsersController {

    @Autowired
    private UsersService usersService;

    /**
     * 添加用户表。
     *
     * @param users 用户表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @ApiOperation("保存用户表")
    public boolean save(@RequestBody @ApiParam("用户表") Users users) {
        return usersService.save(users);
    }

    /**
     * 根据主键删除用户表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @ApiOperation("根据主键用户表")
    public boolean remove(@PathVariable @ApiParam("用户表主键") Long id) {
        return usersService.removeById(id);
    }

    /**
     * 根据主键更新用户表。
     *
     * @param users 用户表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @ApiOperation("根据主键更新用户表")
    public boolean update(@RequestBody @ApiParam("用户表主键") Users users) {
        return usersService.updateById(users);
    }

    /**
     * 查询所有用户表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @ApiOperation("查询所有用户表")
    public List<Users> list() {
        return usersService.list();
    }

    /**
     * 根据用户表主键获取详细信息。
     *
     * @param id 用户表主键
     * @return 用户表详情
     */
    @GetMapping("getInfo/{id}")
    @ApiOperation("根据主键获取用户表")
    public Users getInfo(@PathVariable @ApiParam("用户表主键") Long id) {
        return usersService.getById(id);
    }

    /**
     * 分页查询用户表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @ApiOperation("分页查询用户表")
    public Page<Users> page(@ApiParam("分页信息") Page<Users> page) {
        return usersService.page(page);
    }

}
