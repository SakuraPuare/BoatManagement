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
import com.sakurapuare.boatmanagement.pojo.entity.Merchants;
import com.sakurapuare.boatmanagement.service.base.BaseMerchantsService;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;

/**
 * 商家表 控制层。
 *
 * @author sakurapuare
 * @since 2025-02-08
 */
@RestController
@Api("商家表接口")
@RequestMapping("/merchants")
public class SuperAdminMerchantsController {

    @Autowired
    private BaseMerchantsService baseMerchantsService;

    /**
     * 添加商家表。
     *
     * @param merchants 商家表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @ApiOperation("保存商家表")
    public boolean save(@RequestBody @ApiParam("商家表") Merchants merchants) {
        return baseMerchantsService.save(merchants);
    }

    /**
     * 根据主键删除商家表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @ApiOperation("根据主键商家表")
    public boolean remove(@PathVariable @ApiParam("商家表主键") BigInteger id) {
        return baseMerchantsService.removeById(id);
    }

    /**
     * 根据主键更新商家表。
     *
     * @param merchants 商家表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @ApiOperation("根据主键更新商家表")
    public boolean update(@RequestBody @ApiParam("商家表主键") Merchants merchants) {
        return baseMerchantsService.updateById(merchants);
    }

    /**
     * 查询所有商家表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @ApiOperation("查询所有商家表")
    public List<Merchants> list() {
        return baseMerchantsService.list();
    }

    /**
     * 根据商家表主键获取详细信息。
     *
     * @param id 商家表主键
     * @return 商家表详情
     */
    @GetMapping("getInfo/{id}")
    @ApiOperation("根据主键获取商家表")
    public Merchants getInfo(@PathVariable @ApiParam("商家表主键") BigInteger id) {
        return baseMerchantsService.getById(id);
    }

    /**
     * 分页查询商家表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @ApiOperation("分页查询商家表")
    public Page<Merchants> page(@ApiParam("分页信息") Page<Merchants> page) {
        return baseMerchantsService.page(page);
    }

}
