package com.sakurapuare.boatmanagement.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.entity.Merchants;
import com.sakurapuare.boatmanagement.service.MerchantsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/merchant")
@Tag(name = "管理员-商户模块", description = "商户模块")
@RequiredArgsConstructor
public class AdminMerchantController {

    private final MerchantsService merchantService;

    @GetMapping("/list")
    @Operation(summary = "获取商户列表")
    public Response<List<Merchants>> list() {
        return Response.success("获取商户列表成功", merchantService.list());
    }

    @GetMapping("/list/page")
    @Operation(summary = "获取商户列表分页")
    public Response<Page<Merchants>> listPage(@RequestParam(defaultValue = "1") Integer page,
                                              @RequestParam(defaultValue = "10") Integer size) {
        return Response.success("获取商户列表分页成功", merchantService.page(new Page<>(page, size)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取商户详情")
    public Response<Merchants> get(@PathVariable Long id) {
        return Response.success("获取商户详情成功", merchantService.getById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新商户")
    public Response<Merchants> update(@PathVariable Long id, @RequestBody Merchants merchants) {
        merchants.setId(id);
        merchantService.updateById(merchants);
        return Response.success("更新商户成功", merchants);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除商户")
    public Response<Boolean> delete(@PathVariable Long id) {
        merchantService.removeById(id);
        return Response.success("删除商户成功", true);
    }

    @PostMapping("/create")
    @Operation(summary = "创建商户")
    public Response<Merchants> create(@RequestBody Merchants merchants) {
        merchantService.save(merchants);
        return Response.success("创建商户成功", merchants);
    }
}
