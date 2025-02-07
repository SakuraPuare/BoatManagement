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
import com.sakurapuare.boatmanagement.pojo.entity.Logs;
import com.sakurapuare.boatmanagement.service.base.BaseLogsService;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;

/**
 * 系统日志表 控制层。
 *
 * @author sakurapuare
 * @since 2025-02-08
 */
@RestController
@Api("系统日志表接口")
@RequestMapping("/logs")
public class SuperAdminLogsController {

    @Autowired
    private BaseLogsService baseLogsService;

    /**
     * 添加系统日志表。
     *
     * @param logs 系统日志表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @ApiOperation("保存系统日志表")
    public boolean save(@RequestBody @ApiParam("系统日志表") Logs logs) {
        return baseLogsService.save(logs);
    }

    /**
     * 根据主键删除系统日志表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @ApiOperation("根据主键系统日志表")
    public boolean remove(@PathVariable @ApiParam("系统日志表主键") BigInteger id) {
        return baseLogsService.removeById(id);
    }

    /**
     * 根据主键更新系统日志表。
     *
     * @param logs 系统日志表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @ApiOperation("根据主键更新系统日志表")
    public boolean update(@RequestBody @ApiParam("系统日志表主键") Logs logs) {
        return baseLogsService.updateById(logs);
    }

    /**
     * 查询所有系统日志表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @ApiOperation("查询所有系统日志表")
    public List<Logs> list() {
        return baseLogsService.list();
    }

    /**
     * 根据系统日志表主键获取详细信息。
     *
     * @param id 系统日志表主键
     * @return 系统日志表详情
     */
    @GetMapping("getInfo/{id}")
    @ApiOperation("根据主键获取系统日志表")
    public Logs getInfo(@PathVariable @ApiParam("系统日志表主键") BigInteger id) {
        return baseLogsService.getById(id);
    }

    /**
     * 分页查询系统日志表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @ApiOperation("分页查询系统日志表")
    public Page<Logs> page(@ApiParam("分页信息") Page<Logs> page) {
        return baseLogsService.page(page);
    }

}
