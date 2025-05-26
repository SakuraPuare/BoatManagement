package com.sakurapuare.boatmanagement.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseAccountsDTO;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseUserCertifyDTO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseAccountsVO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseUserCertifyVO;
import com.sakurapuare.boatmanagement.service.AccountsService;
import com.sakurapuare.boatmanagement.service.UserCertifyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/user")
@Tag(name = "AdminUser", description = "管理员用户管理模块")
@RequiredArgsConstructor
public class AdminUserController {

    private final AccountsService accountsService;
    private final UserCertifyService userCertifyService;

    @PostMapping("/list")
    @Operation(summary = "获取用户列表")
    public Response<List<BaseAccountsVO>> adminGetUserList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseAccountsDTO filter) {
        return Response.success("获取用户列表成功", 
                accountsService.adminGetUserList(search, sort, startDateTime, endDateTime, filter));
    }

    @PostMapping("/page")
    @Operation(summary = "分页获取用户列表")
    public Response<Page<BaseAccountsVO>> adminGetUserPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseAccountsDTO filter) {
        return Response.success("获取用户列表分页成功", 
                accountsService.adminGetUserPage(pageNum, pageSize, search, sort, startDateTime, endDateTime, filter));
    }

    @GetMapping("/ids")
    @Operation(summary = "根据 ID 获取用户列表")
    public Response<List<BaseAccountsVO>> adminGetUserByIds(@RequestParam String ids) {
        return Response.success("根据 ID 获取用户列表成功", accountsService.adminGetUserByIds(ids));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取用户详情")
    public Response<BaseAccountsVO> adminGetUser(@PathVariable Long id) {
        List<BaseAccountsVO> users = accountsService.adminGetUserByIds(id.toString());
        if (users.isEmpty()) {
            return Response.error("用户不存在");
        }
        return Response.success(users.get(0));
    }

    @PostMapping
    @Operation(summary = "创建用户")
    public Response<BaseAccountsVO> adminCreateUser(@RequestBody BaseAccountsDTO baseAccountsDTO) {
        return Response.success(accountsService.adminCreateUser(baseAccountsDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新用户")
    public Response<BaseAccountsVO> adminUpdateUser(@PathVariable Long id, @RequestBody BaseAccountsDTO baseAccountsDTO) {
        return Response.success(accountsService.adminUpdateUser(id, baseAccountsDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户")
    public Response<Void> adminDeleteUser(@PathVariable Long id) {
        accountsService.adminDeleteUser(id);
        return Response.success();
    }

    /*
     * 用户认证相关接口
     */

    @PostMapping("/certify/list")
    @Operation(summary = "获取用户认证列表")
    public Response<List<BaseUserCertifyVO>> adminGetUserCertifyList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseUserCertifyDTO filter) {
        return Response.success("获取用户认证列表成功", 
                userCertifyService.adminGetUserCertifyList(search, sort, startDateTime, endDateTime, filter));
    }

    @PostMapping("/certify/page")
    @Operation(summary = "分页获取用户认证列表")
    public Response<Page<BaseUserCertifyVO>> adminGetUserCertifyPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseUserCertifyDTO filter) {
        return Response.success("获取用户认证列表分页成功", 
                userCertifyService.adminGetUserCertifyPage(pageNum, pageSize, search, sort, startDateTime, endDateTime, filter));
    }

    @GetMapping("/certify/ids")
    @Operation(summary = "根据 ID 获取用户认证列表")
    public Response<List<BaseUserCertifyVO>> adminGetUserCertifyByIds(@RequestParam String ids) {
        return Response.success("根据 ID 获取用户认证列表成功", userCertifyService.adminGetUserCertifyByIds(ids));
    }

    @GetMapping("/certify/user/{id}")
    @Operation(summary = "获取用户认证详情")
    public Response<BaseUserCertifyVO> adminGetUserCertify(@PathVariable Long id) {
        List<BaseUserCertifyVO> certifies = userCertifyService.adminGetUserCertifyByIds(id.toString());
        if (certifies.isEmpty()) {
            return Response.error("用户认证不存在");
        }
        return Response.success(certifies.get(0));
    }
}
