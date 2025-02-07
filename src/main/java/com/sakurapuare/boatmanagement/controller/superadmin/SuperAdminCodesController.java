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
import com.sakurapuare.boatmanagement.pojo.entity.Codes;
import com.sakurapuare.boatmanagement.service.CodesService;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;

/**
 * 验证码表 控制层。
 *
 * @author sakurapuare
 * @since 2024-12-26
 */
@RestController
@Api("验证码表接口")
@RequestMapping("/superadmin/codes")
public class SuperAdminCodesController {

    @Autowired
    private CodesService codesService;

    /**
     * 添加验证码表。
     *
     * @param codes 验证码表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @ApiOperation("保存验证码表")
    public boolean save(@RequestBody @ApiParam("验证码表") Codes codes) {
        return codesService.save(codes);
    }

    /**
     * 根据主键删除验证码表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @ApiOperation("根据主键验证码表")
    public boolean remove(@PathVariable @ApiParam("验证码表主键") Long id) {
        return codesService.removeById(id);
    }

    /**
     * 根据主键更新验证码表。
     *
     * @param codes 验证码表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @ApiOperation("根据主键更新验证码表")
    public boolean update(@RequestBody @ApiParam("验证码表主键") Codes codes) {
        return codesService.updateById(codes);
    }

    /**
     * 查询所有验证码表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @ApiOperation("查询所有验证码表")
    public List<Codes> list() {
        return codesService.list();
    }

    /**
     * 根据验证码表主键获取详细信息。
     *
     * @param id 验证码表主键
     * @return 验证码表详情
     */
    @GetMapping("getInfo/{id}")
    @ApiOperation("根据主键获取验证码表")
    public Codes getInfo(@PathVariable @ApiParam("验证码表主键") Long id) {
        return codesService.getById(id);
    }

    /**
     * 分页查询验证码表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @ApiOperation("分页查询验证码表")
    public Page<Codes> page(@ApiParam("分页信息") Page<Codes> page) {
        return codesService.page(page);
    }

}
