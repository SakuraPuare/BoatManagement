package com.sakurapuare.boatmanagement.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.pojo.entity.Alerts;
import com.sakurapuare.boatmanagement.service.AlertsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 告警表 控制层。
 *
 * @author sakurapuare
 * @since 2024-12-17
 */
@RestController
@Api("告警表接口")
@RequestMapping("/admin/alerts")
public class AdminAlertsController {

    @Autowired
    private AlertsService alertsService;

    /**
     * 添加告警表。
     *
     * @param alerts 告警表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @ApiOperation("保存告警表")
    public boolean save(@RequestBody @ApiParam("告警表") Alerts alerts) {
        return alertsService.save(alerts);
    }

    /**
     * 根据主键删除告警表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @ApiOperation("根据主键告警表")
    public boolean remove(@PathVariable @ApiParam("告警表主键") Long id) {
        return alertsService.removeById(id);
    }

    /**
     * 根据主键更新告警表。
     *
     * @param alerts 告警表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @ApiOperation("根据主键更新告警表")
    public boolean update(@RequestBody @ApiParam("告警表主键") Alerts alerts) {
        return alertsService.updateById(alerts);
    }

    /**
     * 查询所有告警表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @ApiOperation("查询所有告警表")
    public List<Alerts> list() {
        return alertsService.list();
    }

    /**
     * 根据告警表主键获取详细信息。
     *
     * @param id 告警表主键
     * @return 告警表详情
     */
    @GetMapping("getInfo/{id}")
    @ApiOperation("根据主键获取告警表")
    public Alerts getInfo(@PathVariable @ApiParam("告警表主键") Long id) {
        return alertsService.getById(id);
    }

    /**
     * 分页查询告警表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @ApiOperation("分页查询告警表")
    public Page<Alerts> page(@ApiParam("分页信息") Page<Alerts> page) {
        return alertsService.page(page);
    }

}
