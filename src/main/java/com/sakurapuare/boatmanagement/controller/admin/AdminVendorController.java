package com.sakurapuare.boatmanagement.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseVendorsDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Vendors;
import com.sakurapuare.boatmanagement.service.VendorsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/vendor")
@Tag(name = "管理员-供应商模块", description = "供应商模块")
@RequiredArgsConstructor
public class AdminVendorController {

    private final VendorsService vendorService;

    @GetMapping("/list")
    @Operation(summary = "获取供应商列表")
    public Response<List<Vendors>> list() {
        return Response.success("获取供应商列表成功", vendorService.list());
    }

    @GetMapping("/list/page")
    @Operation(summary = "获取供应商列表分页")
    public Response<Page<Vendors>> listPage(@RequestParam(defaultValue = "1") Integer page,
                                            @RequestParam(defaultValue = "10") Integer size) {
        return Response.success("获取供应商列表分页成功", vendorService.page(new Page<>(page, size)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取供应商详情")
    public Response<Vendors> get(@PathVariable Long id) {
        return Response.success("获取供应商详情成功", vendorService.getById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新供应商")
    public Response<Vendors> update(@PathVariable Long id, @RequestBody BaseVendorsDTO baseVendorsDTO) {
        Vendors vendors = new Vendors();
        BeanUtils.copyProperties(baseVendorsDTO, vendors);
        vendors.setId(id);
        vendorService.updateById(vendors);
        return Response.success("更新供应商成功", vendors);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除供应商")
    public Response<Boolean> delete(@PathVariable Long id) {
        vendorService.removeById(id);
        return Response.success("删除供应商成功", true);
    }

    @PostMapping("/create")
    @Operation(summary = "创建供应商")
    public Response<Vendors> create(@RequestBody BaseVendorsDTO baseVendorsDTO) {
        Vendors vendors = new Vendors();
        BeanUtils.copyProperties(baseVendorsDTO, vendors);
        vendorService.save(vendors);
        return Response.success("创建供应商成功", vendors);
    }
}
