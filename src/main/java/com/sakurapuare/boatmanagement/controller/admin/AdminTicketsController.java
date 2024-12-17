package com.sakurapuare.boatmanagement.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.pojo.entity.Tickets;
import com.sakurapuare.boatmanagement.service.TicketsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 船票表 控制层。
 *
 * @author sakurapuare
 * @since 2024-12-17
 */
@RestController
@Api("船票表接口")
@RequestMapping("/admin/tickets")
public class AdminTicketsController {

    @Autowired
    private TicketsService ticketsService;

    /**
     * 添加船票表。
     *
     * @param tickets 船票表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @ApiOperation("保存船票表")
    public boolean save(@RequestBody @ApiParam("船票表") Tickets tickets) {
        return ticketsService.save(tickets);
    }

    /**
     * 根据主键删除船票表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @ApiOperation("根据主键船票表")
    public boolean remove(@PathVariable @ApiParam("船票表主键") Long id) {
        return ticketsService.removeById(id);
    }

    /**
     * 根据主键更新船票表。
     *
     * @param tickets 船票表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @ApiOperation("根据主键更新船票表")
    public boolean update(@RequestBody @ApiParam("船票表主键") Tickets tickets) {
        return ticketsService.updateById(tickets);
    }

    /**
     * 查询所有船票表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @ApiOperation("查询所有船票表")
    public List<Tickets> list() {
        return ticketsService.list();
    }

    /**
     * 根据船票表主键获取详细信息。
     *
     * @param id 船票表主键
     * @return 船票表详情
     */
    @GetMapping("getInfo/{id}")
    @ApiOperation("根据主键获取船票表")
    public Tickets getInfo(@PathVariable @ApiParam("船票表主键") Long id) {
        return ticketsService.getById(id);
    }

    /**
     * 分页查询船票表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @ApiOperation("分页查询船票表")
    public Page<Tickets> page(@ApiParam("分页信息") Page<Tickets> page) {
        return ticketsService.page(page);
    }

}
