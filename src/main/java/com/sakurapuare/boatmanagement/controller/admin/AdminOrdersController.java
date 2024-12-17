package com.sakurapuare.boatmanagement.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.pojo.entity.Orders;
import com.sakurapuare.boatmanagement.service.OrdersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单表 控制层。
 *
 * @author sakurapuare
 * @since 2024-12-17
 */
@RestController
@Api("订单表接口")
@RequestMapping("/admin/orders")
public class AdminOrdersController {

    @Autowired
    private OrdersService ordersService;

    /**
     * 添加订单表。
     *
     * @param orders 订单表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @ApiOperation("保存订单表")
    public boolean save(@RequestBody @ApiParam("订单表") Orders orders) {
        return ordersService.save(orders);
    }

    /**
     * 根据主键删除订单表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @ApiOperation("根据主键订单表")
    public boolean remove(@PathVariable @ApiParam("订单表主键") Long id) {
        return ordersService.removeById(id);
    }

    /**
     * 根据主键更新订单表。
     *
     * @param orders 订单表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @ApiOperation("根据主键更新订单表")
    public boolean update(@RequestBody @ApiParam("订单表主键") Orders orders) {
        return ordersService.updateById(orders);
    }

    /**
     * 查询所有订单表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @ApiOperation("查询所有订单表")
    public List<Orders> list() {
        return ordersService.list();
    }

    /**
     * 根据订单表主键获取详细信息。
     *
     * @param id 订单表主键
     * @return 订单表详情
     */
    @GetMapping("getInfo/{id}")
    @ApiOperation("根据主键获取订单表")
    public Orders getInfo(@PathVariable @ApiParam("订单表主键") Long id) {
        return ordersService.getById(id);
    }

    /**
     * 分页查询订单表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @ApiOperation("分页查询订单表")
    public Page<Orders> page(@ApiParam("分页信息") Page<Orders> page) {
        return ordersService.page(page);
    }

}
