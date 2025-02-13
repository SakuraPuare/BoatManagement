package com.sakurapuare.boatmanagement.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseVendorsDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Vendors;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseVendorsVO;
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
    public Response<List<BaseVendorsVO>> list(@RequestParam BaseVendorsDTO queryDTO) {
        return Response.success("获取供应商列表成功", vendorService.getListQuery(queryDTO));
    }

    @GetMapping("/page")
    @Operation(summary = "获取供应商列表分页")
    public Response<Page<BaseVendorsVO>> listPage(@RequestParam(defaultValue = "1") Integer page,
                                                  @RequestParam(defaultValue = "10") Integer size, @RequestParam BaseVendorsDTO queryDTO) {
        return Response.success("获取供应商列表分页成功", vendorService.getPageQuery(page, size, queryDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取供应商详情")
    public Response<BaseVendorsVO> get(@PathVariable Long id) {
        Vendors vendors = vendorService.getById(id);
        BaseVendorsVO vendorsVO = new BaseVendorsVO();
        BeanUtils.copyProperties(vendors, vendorsVO);
        return Response.success("获取供应商详情成功", vendorsVO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新供应商")
    public Response<String> update(@PathVariable Long id, @RequestBody BaseVendorsDTO baseVendorsDTO) {
        vendorService.updateVendor(id, baseVendorsDTO);
        return Response.success("更新供应商成功");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除供应商")
    public Response<String> delete(@PathVariable Long id) {
        vendorService.deleteVendor(id);
        return Response.success("删除供应商成功");
    }

    @PostMapping("/")
    @Operation(summary = "创建供应商")
    public Response<String> create(@RequestBody BaseVendorsDTO baseVendorsDTO) {
        vendorService.addVendor(baseVendorsDTO);
        return Response.success("创建供应商成功");
    }
}
