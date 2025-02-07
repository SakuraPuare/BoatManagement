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
import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
import com.sakurapuare.boatmanagement.service.base.BaseAccountsService;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;

/**
 * 基础账号表 控制层。
 *
 * @author sakurapuare
 * @since 2025-02-08
 */
@RestController
@Api("基础账号表接口")
@RequestMapping("/accounts")
public class SuperAdminAccountsController {

    @Autowired
    private BaseAccountsService baseAccountsService;

    /**
     * 添加基础账号表。
     *
     * @param accounts 基础账号表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @ApiOperation("保存基础账号表")
    public boolean save(@RequestBody @ApiParam("基础账号表") Accounts accounts) {
        return baseAccountsService.save(accounts);
    }

    /**
     * 根据主键删除基础账号表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @ApiOperation("根据主键基础账号表")
    public boolean remove(@PathVariable @ApiParam("基础账号表主键") BigInteger id) {
        return baseAccountsService.removeById(id);
    }

    /**
     * 根据主键更新基础账号表。
     *
     * @param accounts 基础账号表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @ApiOperation("根据主键更新基础账号表")
    public boolean update(@RequestBody @ApiParam("基础账号表主键") Accounts accounts) {
        return baseAccountsService.updateById(accounts);
    }

    /**
     * 查询所有基础账号表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @ApiOperation("查询所有基础账号表")
    public List<Accounts> list() {
        return baseAccountsService.list();
    }

    /**
     * 根据基础账号表主键获取详细信息。
     *
     * @param id 基础账号表主键
     * @return 基础账号表详情
     */
    @GetMapping("getInfo/{id}")
    @ApiOperation("根据主键获取基础账号表")
    public Accounts getInfo(@PathVariable @ApiParam("基础账号表主键") BigInteger id) {
        return baseAccountsService.getById(id);
    }

    /**
     * 分页查询基础账号表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @ApiOperation("分页查询基础账号表")
    public Page<Accounts> page(@ApiParam("分页信息") Page<Accounts> page) {
        return baseAccountsService.page(page);
    }

}
