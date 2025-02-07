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
import com.sakurapuare.boatmanagement.pojo.entity.SocialAuth;
import com.sakurapuare.boatmanagement.service.base.BaseSocialAuthService;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;

/**
 * 第三方登录表 控制层。
 *
 * @author sakurapuare
 * @since 2025-02-08
 */
@RestController
@Api("第三方登录表接口")
@RequestMapping("/socialAuth")
public class SuperAdminSocialAuthController {

    @Autowired
    private BaseSocialAuthService baseSocialAuthService;

    /**
     * 添加第三方登录表。
     *
     * @param socialAuth 第三方登录表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @ApiOperation("保存第三方登录表")
    public boolean save(@RequestBody @ApiParam("第三方登录表") SocialAuth socialAuth) {
        return baseSocialAuthService.save(socialAuth);
    }

    /**
     * 根据主键删除第三方登录表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @ApiOperation("根据主键第三方登录表")
    public boolean remove(@PathVariable @ApiParam("第三方登录表主键") BigInteger id) {
        return baseSocialAuthService.removeById(id);
    }

    /**
     * 根据主键更新第三方登录表。
     *
     * @param socialAuth 第三方登录表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @ApiOperation("根据主键更新第三方登录表")
    public boolean update(@RequestBody @ApiParam("第三方登录表主键") SocialAuth socialAuth) {
        return baseSocialAuthService.updateById(socialAuth);
    }

    /**
     * 查询所有第三方登录表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @ApiOperation("查询所有第三方登录表")
    public List<SocialAuth> list() {
        return baseSocialAuthService.list();
    }

    /**
     * 根据第三方登录表主键获取详细信息。
     *
     * @param id 第三方登录表主键
     * @return 第三方登录表详情
     */
    @GetMapping("getInfo/{id}")
    @ApiOperation("根据主键获取第三方登录表")
    public SocialAuth getInfo(@PathVariable @ApiParam("第三方登录表主键") BigInteger id) {
        return baseSocialAuthService.getById(id);
    }

    /**
     * 分页查询第三方登录表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @ApiOperation("分页查询第三方登录表")
    public Page<SocialAuth> page(@ApiParam("分页信息") Page<SocialAuth> page) {
        return baseSocialAuthService.page(page);
    }

}
