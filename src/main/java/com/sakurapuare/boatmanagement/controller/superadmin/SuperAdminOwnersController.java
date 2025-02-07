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
import com.sakurapuare.boatmanagement.pojo.entity.Owners;
import com.sakurapuare.boatmanagement.service.base.BaseOwnersService;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;

/**
 * 船主表 控制层。
 *
 * @author sakurapuare
 * @since 2025-02-08
 */
@RestController
@Api("船主表接口")
@RequestMapping("/owners")
public class SuperAdminOwnersController {

    @Autowired
    private BaseOwnersService baseOwnersService;

    /**
     * 添加船主表。
     *
     * @param owners 船主表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @ApiOperation("保存船主表")
    public boolean save(@RequestBody @ApiParam("船主表") Owners owners) {
        return baseOwnersService.save(owners);
    }

    /**
     * 根据主键删除船主表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @ApiOperation("根据主键船主表")
    public boolean remove(@PathVariable @ApiParam("船主表主键") BigInteger id) {
        return baseOwnersService.removeById(id);
    }

    /**
     * 根据主键更新船主表。
     *
     * @param owners 船主表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @ApiOperation("根据主键更新船主表")
    public boolean update(@RequestBody @ApiParam("船主表主键") Owners owners) {
        return baseOwnersService.updateById(owners);
    }

    /**
     * 查询所有船主表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @ApiOperation("查询所有船主表")
    public List<Owners> list() {
        return baseOwnersService.list();
    }

    /**
     * 根据船主表主键获取详细信息。
     *
     * @param id 船主表主键
     * @return 船主表详情
     */
    @GetMapping("getInfo/{id}")
    @ApiOperation("根据主键获取船主表")
    public Owners getInfo(@PathVariable @ApiParam("船主表主键") BigInteger id) {
        return baseOwnersService.getById(id);
    }

    /**
     * 分页查询船主表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @ApiOperation("分页查询船主表")
    public Page<Owners> page(@ApiParam("分页信息") Page<Owners> page) {
        return baseOwnersService.page(page);
    }

}
