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
@Tag(name = "AdminUser", description = "用户模块")
@RequiredArgsConstructor
public class AdminUserController {

    private final AccountsService accountsService;
    private final UserCertifyService userCertifyService;

    @GetMapping("/list")
    @Operation(summary = "获取用户列表")
    public Response<List<BaseAccountsVO>> adminGetUserList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseAccountsDTO filter) {
        return Response.success(accountsService.adminGetUserList(search, sort, startDateTime, endDateTime, filter));
    }

    @GetMapping("/page")
    @Operation(summary = "获取用户列表分页")
    public Response<Page<BaseAccountsVO>> adminGetUserPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseAccountsDTO filter) {
        return Response.success(accountsService.adminGetUserPage(pageNum, pageSize, search, sort, startDateTime, endDateTime, filter));
    }

    @GetMapping("/ids/{ids}")
    @Operation(summary = "根据ID获取用户")
    public Response<List<BaseAccountsVO>> adminGetUserByIds(@PathVariable String ids) {
        return Response.success(accountsService.adminGetUserByIds(ids));
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

    @GetMapping("/certify/user/list")
    @Operation(summary = "获取用户认证列表")
    public Response<List<BaseUserCertifyVO>> adminGetUserCertifyList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseUserCertifyDTO filter) {
        return Response.success(userCertifyService.adminGetUserCertifyList(search, sort, startDateTime, endDateTime, filter));
    }

    @GetMapping("/certify/user/page")
    @Operation(summary = "获取用户认证列表分页")
    public Response<Page<BaseUserCertifyVO>> adminGetUserCertifyPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseUserCertifyDTO filter) {
        return Response.success(userCertifyService.adminGetUserCertifyPage(pageNum, pageSize, search, sort, startDateTime, endDateTime, filter));
    }

    @GetMapping("/certify/user/ids/{ids}")
    @Operation(summary = "根据ID获取用户认证")
    public Response<List<BaseUserCertifyVO>> adminGetUserCertifyByIds(@PathVariable String ids) {
        return Response.success(userCertifyService.adminGetUserCertifyByIds(ids));
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
