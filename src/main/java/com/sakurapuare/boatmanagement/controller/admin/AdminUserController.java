package com.sakurapuare.boatmanagement.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
import com.sakurapuare.boatmanagement.service.AccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/user")
@Tag(name = "管理员-用户模块", description = "用户模块")
@RequiredArgsConstructor
public class AdminUserController {

    private final AccountsService accountsService;

    @GetMapping("/list")
    @Operation(summary = "获取用户列表")
    public Response<List<Accounts>> list() {
        return Response.success("获取用户列表成功", accountsService.list());
    }

    @GetMapping("/list/page")
    @Operation(summary = "获取用户列表分页")
    public Response<Page<Accounts>> listPage(@RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam(defaultValue = "10") Integer size) {
        return Response.success("获取用户列表分页成功", accountsService.page(new Page<>(page, size)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取用户详情")
    public Response<Accounts> get(@PathVariable Long id) {
        return Response.success("获取用户详情成功", accountsService.getById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新用户")
    public Response<Accounts> update(@PathVariable Long id, @RequestBody Accounts accounts) {
        accounts.setId(id);
        accountsService.updateById(accounts);
        return Response.success("更新用户成功", accounts);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户")
    public Response<Boolean> delete(@PathVariable Long id) {
        accountsService.removeById(id);
        return Response.success("删除用户成功", true);
    }

    @PostMapping("/create")
    @Operation(summary = "创建用户")
    public Response<Accounts> create(@RequestBody Accounts accounts) {
        accountsService.save(accounts);
        return Response.success("创建用户成功", accounts);
    }
}
