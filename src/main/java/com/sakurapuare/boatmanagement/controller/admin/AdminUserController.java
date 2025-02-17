package com.sakurapuare.boatmanagement.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseAccountsDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseAccountsVO;
import com.sakurapuare.boatmanagement.service.AccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/user")
@Tag(name = "AdminUser", description = "用户模块")
@RequiredArgsConstructor
public class AdminUserController {

    private final AccountsService accountsService;

    @PostMapping("/list")
    @Operation(summary = "获取用户列表")
    public Response<List<BaseAccountsVO>> getAdminUserListQuery(@RequestBody BaseAccountsDTO queryDTO) {
        return Response.success("获取用户列表成功", accountsService.getAdminUserListQuery(queryDTO));
    }

    @PostMapping("/page")
    @Operation(summary = "获取用户列表分页")
    public Response<Page<BaseAccountsVO>> getAdminUserPageQuery(@RequestParam(defaultValue = "1") Integer page,
                                                   @RequestParam(defaultValue = "10") Integer size, @RequestBody BaseAccountsDTO queryDTO) {
        return Response.success("获取用户列表分页成功", accountsService.getAdminUserPageQuery(page, size, queryDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取用户详情")
    public Response<BaseAccountsVO> getAdminUser(@PathVariable Long id) {
        Accounts accounts = accountsService.getById(id);
        BaseAccountsVO accountsVO = new BaseAccountsVO();
        BeanUtils.copyProperties(accounts, accountsVO);
        return Response.success("获取用户详情成功", accountsVO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新用户")
    public Response<String> updateAdminAccount(@PathVariable Long id, @RequestBody BaseAccountsDTO baseAccountsDTO) {
        accountsService.updateAdminAccount(id, baseAccountsDTO);
        return Response.success("更新用户成功");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户")
    public Response<String> deleteAdminAccount(@PathVariable Long id) {
        accountsService.deleteAdminAccount(id);
        return Response.success("删除用户成功");
    }

    @PostMapping("/")
    @Operation(summary = "创建用户")
    public Response<String> createAdminAccount(@RequestBody BaseAccountsDTO baseAccountsDTO) {
        accountsService.createAdminAccount(baseAccountsDTO);
        return Response.success("创建用户成功");
    }
}
