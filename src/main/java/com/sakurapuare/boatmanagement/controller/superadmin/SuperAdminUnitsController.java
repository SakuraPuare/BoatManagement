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
import com.sakurapuare.boatmanagement.pojo.entity.Units;
import com.sakurapuare.boatmanagement.service.base.BaseUnitsService;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;

/**
 * 单位表 控制层。
 *
 * @author sakurapuare
 * @since 2025-02-08
 */
@RestController
@Api("单位表接口")
@RequestMapping("/units")
public class SuperAdminUnitsController {

    @Autowired
    private BaseUnitsService baseUnitsService;

    /**
     * 添加单位表。
     *
     * @param units 单位表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @ApiOperation("保存单位表")
    public boolean save(@RequestBody @ApiParam("单位表") Units units) {
        return baseUnitsService.save(units);
    }

    /**
     * 根据主键删除单位表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @ApiOperation("根据主键单位表")
    public boolean remove(@PathVariable @ApiParam("单位表主键") BigInteger id) {
        return baseUnitsService.removeById(id);
    }

    /**
     * 根据主键更新单位表。
     *
     * @param units 单位表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @ApiOperation("根据主键更新单位表")
    public boolean update(@RequestBody @ApiParam("单位表主键") Units units) {
        return baseUnitsService.updateById(units);
    }

    /**
     * 查询所有单位表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @ApiOperation("查询所有单位表")
    public List<Units> list() {
        return baseUnitsService.list();
    }

    /**
     * 根据单位表主键获取详细信息。
     *
     * @param id 单位表主键
     * @return 单位表详情
     */
    @GetMapping("getInfo/{id}")
    @ApiOperation("根据主键获取单位表")
    public Units getInfo(@PathVariable @ApiParam("单位表主键") BigInteger id) {
        return baseUnitsService.getById(id);
    }

    /**
     * 分页查询单位表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @ApiOperation("分页查询单位表")
    public Page<Units> page(@ApiParam("分页信息") Page<Units> page) {
        return baseUnitsService.page(page);
    }

}
