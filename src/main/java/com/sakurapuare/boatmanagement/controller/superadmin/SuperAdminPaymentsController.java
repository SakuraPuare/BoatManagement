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
import com.sakurapuare.boatmanagement.pojo.entity.Payments;
import com.sakurapuare.boatmanagement.service.PaymentsService;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;

/**
 * 支付记录表 控制层。
 *
 * @author sakurapuare
 * @since 2024-12-26
 */
@RestController
@Api("支付记录表接口")
@RequestMapping("/superadmin/payments")
public class SuperAdminPaymentsController {

    @Autowired
    private PaymentsService paymentsService;

    /**
     * 添加支付记录表。
     *
     * @param payments 支付记录表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @ApiOperation("保存支付记录表")
    public boolean save(@RequestBody @ApiParam("支付记录表") Payments payments) {
        return paymentsService.save(payments);
    }

    /**
     * 根据主键删除支付记录表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @ApiOperation("根据主键支付记录表")
    public boolean remove(@PathVariable @ApiParam("支付记录表主键") Long id) {
        return paymentsService.removeById(id);
    }

    /**
     * 根据主键更新支付记录表。
     *
     * @param payments 支付记录表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @ApiOperation("根据主键更新支付记录表")
    public boolean update(@RequestBody @ApiParam("支付记录表主键") Payments payments) {
        return paymentsService.updateById(payments);
    }

    /**
     * 查询所有支付记录表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @ApiOperation("查询所有支付记录表")
    public List<Payments> list() {
        return paymentsService.list();
    }

    /**
     * 根据支付记录表主键获取详细信息。
     *
     * @param id 支付记录表主键
     * @return 支付记录表详情
     */
    @GetMapping("getInfo/{id}")
    @ApiOperation("根据主键获取支付记录表")
    public Payments getInfo(@PathVariable @ApiParam("支付记录表主键") Long id) {
        return paymentsService.getById(id);
    }

    /**
     * 分页查询支付记录表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @ApiOperation("分页查询支付记录表")
    public Page<Payments> page(@ApiParam("分页信息") Page<Payments> page) {
        return paymentsService.page(page);
    }

}
