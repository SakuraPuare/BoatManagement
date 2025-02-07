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
import com.sakurapuare.boatmanagement.pojo.entity.CaptchaLimit;
import com.sakurapuare.boatmanagement.service.base.BaseCaptchaLimitService;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;

/**
 * 验证码防刷记录 控制层。
 *
 * @author sakurapuare
 * @since 2025-02-08
 */
@RestController
@Api("验证码防刷记录接口")
@RequestMapping("/captchaLimit")
public class SuperAdminCaptchaLimitController {

    @Autowired
    private BaseCaptchaLimitService baseCaptchaLimitService;

    /**
     * 添加验证码防刷记录。
     *
     * @param captchaLimit 验证码防刷记录
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @ApiOperation("保存验证码防刷记录")
    public boolean save(@RequestBody @ApiParam("验证码防刷记录") CaptchaLimit captchaLimit) {
        return baseCaptchaLimitService.save(captchaLimit);
    }

    /**
     * 根据主键删除验证码防刷记录。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @ApiOperation("根据主键验证码防刷记录")
    public boolean remove(@PathVariable @ApiParam("验证码防刷记录主键") BigInteger id) {
        return baseCaptchaLimitService.removeById(id);
    }

    /**
     * 根据主键更新验证码防刷记录。
     *
     * @param captchaLimit 验证码防刷记录
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @ApiOperation("根据主键更新验证码防刷记录")
    public boolean update(@RequestBody @ApiParam("验证码防刷记录主键") CaptchaLimit captchaLimit) {
        return baseCaptchaLimitService.updateById(captchaLimit);
    }

    /**
     * 查询所有验证码防刷记录。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @ApiOperation("查询所有验证码防刷记录")
    public List<CaptchaLimit> list() {
        return baseCaptchaLimitService.list();
    }

    /**
     * 根据验证码防刷记录主键获取详细信息。
     *
     * @param id 验证码防刷记录主键
     * @return 验证码防刷记录详情
     */
    @GetMapping("getInfo/{id}")
    @ApiOperation("根据主键获取验证码防刷记录")
    public CaptchaLimit getInfo(@PathVariable @ApiParam("验证码防刷记录主键") BigInteger id) {
        return baseCaptchaLimitService.getById(id);
    }

    /**
     * 分页查询验证码防刷记录。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @ApiOperation("分页查询验证码防刷记录")
    public Page<CaptchaLimit> page(@ApiParam("分页信息") Page<CaptchaLimit> page) {
        return baseCaptchaLimitService.page(page);
    }

}
