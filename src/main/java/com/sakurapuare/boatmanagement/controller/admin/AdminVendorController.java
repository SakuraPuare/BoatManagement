package com.sakurapuare.boatmanagement.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseVendorsDTO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseVendorsVO;
import com.sakurapuare.boatmanagement.service.VendorsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/vendor")
@Tag(name = "AdminVendor", description = "供应商模块")
@RequiredArgsConstructor
public class AdminVendorController {

    private final VendorsService vendorService;

    @GetMapping("/list")
    @Operation(summary = "获取供应商列表")
    public Response<List<BaseVendorsVO>> getVendorList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseVendorsDTO filter) {
        List<BaseVendorsVO> vendors = vendorService.adminGetVendorList(search, sort, startDateTime, endDateTime, filter);
        return Response.success("获取供应商列表成功", vendors);
    }

    @GetMapping("/page")
    @Operation(summary = "分页获取供应商列表")
    public Response<Page<BaseVendorsVO>> getVendorPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseVendorsDTO filter) {
        Page<BaseVendorsVO> vendorPage = vendorService.adminGetVendorPage(pageNum, pageSize, search, sort, startDateTime, endDateTime, filter);
        return Response.success("分页获取供应商列表成功", vendorPage);
    }

    @GetMapping("/ids")
    @Operation(summary = "根据ID获取供应商")
    public Response<List<BaseVendorsVO>> getVendorByIds(@RequestParam String ids) {
        List<BaseVendorsVO> vendors = vendorService.adminGetVendorByIds(ids);
        return Response.success("根据ID获取供应商成功", vendors);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取供应商详情")
    public Response<BaseVendorsVO> getVendorById(@PathVariable Long id) {
        BaseVendorsVO vendor = vendorService.adminGetVendorById(id);
        return Response.success("获取供应商详情成功", vendor);
    }

    @PostMapping
    @Operation(summary = "创建供应商")
    public Response<BaseVendorsVO> createVendor(@RequestBody BaseVendorsDTO dto) {
        BaseVendorsVO vendor = vendorService.adminCreateVendor(dto);
        return Response.success("创建供应商成功", vendor);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新供应商")
    public Response<BaseVendorsVO> updateVendor(@PathVariable Long id, @RequestBody BaseVendorsDTO dto) {
        BaseVendorsVO vendor = vendorService.adminUpdateVendor(id, dto);
        return Response.success("更新供应商成功", vendor);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除供应商")
    public Response<Void> deleteVendor(@PathVariable Long id) {
        vendorService.adminDeleteVendor(id);
        return Response.success("删除供应商成功", null);
    }
}
