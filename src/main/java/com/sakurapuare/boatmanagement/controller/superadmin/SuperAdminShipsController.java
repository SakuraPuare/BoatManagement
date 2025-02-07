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
import com.sakurapuare.boatmanagement.pojo.entity.Ships;
import com.sakurapuare.boatmanagement.service.base.BaseShipsService;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;

/**
 * 船只表 控制层。
 *
 * @author sakurapuare
 * @since 2025-02-08
 */
@RestController
@Api("船只表接口")
@RequestMapping("/ships")
public class SuperAdminShipsController {

    @Autowired
    private BaseShipsService baseShipsService;

    /**
     * 添加船只表。
     *
     * @param ships 船只表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @ApiOperation("保存船只表")
    public boolean save(@RequestBody @ApiParam("船只表") Ships ships) {
        return baseShipsService.save(ships);
    }

    /**
     * 根据主键删除船只表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @ApiOperation("根据主键船只表")
    public boolean remove(@PathVariable @ApiParam("船只表主键") BigInteger id) {
        return baseShipsService.removeById(id);
    }

    /**
     * 根据主键更新船只表。
     *
     * @param ships 船只表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @ApiOperation("根据主键更新船只表")
    public boolean update(@RequestBody @ApiParam("船只表主键") Ships ships) {
        return baseShipsService.updateById(ships);
    }

    /**
     * 查询所有船只表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @ApiOperation("查询所有船只表")
    public List<Ships> list() {
        return baseShipsService.list();
    }

    /**
     * 根据船只表主键获取详细信息。
     *
     * @param id 船只表主键
     * @return 船只表详情
     */
    @GetMapping("getInfo/{id}")
    @ApiOperation("根据主键获取船只表")
    public Ships getInfo(@PathVariable @ApiParam("船只表主键") BigInteger id) {
        return baseShipsService.getById(id);
    }

    /**
     * 分页查询船只表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @ApiOperation("分页查询船只表")
    public Page<Ships> page(@ApiParam("分页信息") Page<Ships> page) {
        return baseShipsService.page(page);
    }

}
