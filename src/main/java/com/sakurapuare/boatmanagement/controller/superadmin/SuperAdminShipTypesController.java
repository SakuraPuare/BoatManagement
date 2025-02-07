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
import com.sakurapuare.boatmanagement.pojo.entity.ShipTypes;
import com.sakurapuare.boatmanagement.service.base.BaseShipTypesService;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;

/**
 * 船只类型表 控制层。
 *
 * @author sakurapuare
 * @since 2025-02-08
 */
@RestController
@Api("船只类型表接口")
@RequestMapping("/shipTypes")
public class SuperAdminShipTypesController {

    @Autowired
    private BaseShipTypesService baseShipTypesService;

    /**
     * 添加船只类型表。
     *
     * @param shipTypes 船只类型表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @ApiOperation("保存船只类型表")
    public boolean save(@RequestBody @ApiParam("船只类型表") ShipTypes shipTypes) {
        return baseShipTypesService.save(shipTypes);
    }

    /**
     * 根据主键删除船只类型表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @ApiOperation("根据主键船只类型表")
    public boolean remove(@PathVariable @ApiParam("船只类型表主键") Long id) {
        return baseShipTypesService.removeById(id);
    }

    /**
     * 根据主键更新船只类型表。
     *
     * @param shipTypes 船只类型表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @ApiOperation("根据主键更新船只类型表")
    public boolean update(@RequestBody @ApiParam("船只类型表主键") ShipTypes shipTypes) {
        return baseShipTypesService.updateById(shipTypes);
    }

    /**
     * 查询所有船只类型表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @ApiOperation("查询所有船只类型表")
    public List<ShipTypes> list() {
        return baseShipTypesService.list();
    }

    /**
     * 根据船只类型表主键获取详细信息。
     *
     * @param id 船只类型表主键
     * @return 船只类型表详情
     */
    @GetMapping("getInfo/{id}")
    @ApiOperation("根据主键获取船只类型表")
    public ShipTypes getInfo(@PathVariable @ApiParam("船只类型表主键") Long id) {
        return baseShipTypesService.getById(id);
    }

    /**
     * 分页查询船只类型表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @ApiOperation("分页查询船只类型表")
    public Page<ShipTypes> page(@ApiParam("分页信息") Page<ShipTypes> page) {
        return baseShipTypesService.page(page);
    }

}
