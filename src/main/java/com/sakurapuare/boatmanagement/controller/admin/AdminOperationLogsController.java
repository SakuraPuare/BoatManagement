package com.sakurapuare.boatmanagement.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.pojo.entity.OperationLogs;
import com.sakurapuare.boatmanagement.service.OperationLogsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 操作日志表 控制层。
 *
 * @author sakurapuare
 * @since 2024-12-17
 */
@RestController
@Api("操作日志表接口")
@RequestMapping("/admin/operationLogs")
public class AdminOperationLogsController {

    @Autowired
    private OperationLogsService operationLogsService;

    /**
     * 添加操作日志表。
     *
     * @param operationLogs 操作日志表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @ApiOperation("保存操作日志表")
    public boolean save(@RequestBody @ApiParam("操作日志表") OperationLogs operationLogs) {
        return operationLogsService.save(operationLogs);
    }

    /**
     * 根据主键删除操作日志表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @ApiOperation("根据主键操作日志表")
    public boolean remove(@PathVariable @ApiParam("操作日志表主键") Long id) {
        return operationLogsService.removeById(id);
    }

    /**
     * 根据主键更新操作日志表。
     *
     * @param operationLogs 操作日志表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @ApiOperation("根据主键更新操作日志表")
    public boolean update(@RequestBody @ApiParam("操作日志表主键") OperationLogs operationLogs) {
        return operationLogsService.updateById(operationLogs);
    }

    /**
     * 查询所有操作日志表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @ApiOperation("查询所有操作日志表")
    public List<OperationLogs> list() {
        return operationLogsService.list();
    }

    /**
     * 根据操作日志表主键获取详细信息。
     *
     * @param id 操作日志表主键
     * @return 操作日志表详情
     */
    @GetMapping("getInfo/{id}")
    @ApiOperation("根据主键获取操作日志表")
    public OperationLogs getInfo(@PathVariable @ApiParam("操作日志表主键") Long id) {
        return operationLogsService.getById(id);
    }

    /**
     * 分页查询操作日志表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @ApiOperation("分页查询操作日志表")
    public Page<OperationLogs> page(@ApiParam("分页信息") Page<OperationLogs> page) {
        return operationLogsService.page(page);
    }

}
