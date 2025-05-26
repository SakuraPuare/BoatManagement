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
@Tag(name = "AdminVendor", description = "管理员供应商管理模块")
@RequiredArgsConstructor
public class AdminVendorController {

    private final VendorsService vendorsService;

    @PostMapping("/list")
    @Operation(summary = "获取供应商列表")
    public Response<List<BaseVendorsVO>> adminGetVendorList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseVendorsDTO filter) {
        return Response.success("获取供应商列表成功", 
                vendorsService.adminGetVendorList(search, sort, startDateTime, endDateTime, filter));
    }

    @PostMapping("/page")
    @Operation(summary = "分页获取供应商列表")
    public Response<Page<BaseVendorsVO>> adminGetVendorPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseVendorsDTO filter) {
        return Response.success("获取供应商列表分页成功", 
                vendorsService.adminGetVendorPage(pageNum, pageSize, search, sort, startDateTime, endDateTime, filter));
    }

    @GetMapping("/ids")
    @Operation(summary = "根据 ID 获取供应商列表")
    public Response<List<BaseVendorsVO>> adminGetVendorByIds(@RequestParam String ids) {
        return Response.success("根据 ID 获取供应商列表成功", vendorsService.adminGetVendorByIds(ids));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取供应商详情")
    public Response<BaseVendorsVO> adminGetVendor(@PathVariable Long id) {
        return Response.success("获取供应商详情成功", vendorsService.adminGetVendorByIds(id.toString()).get(0));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新供应商")
    public Response<BaseVendorsVO> adminUpdateVendor(@PathVariable Long id, @RequestBody BaseVendorsDTO baseVendorsDTO) {
        return Response.success("更新供应商成功", vendorsService.adminUpdateVendor(id, baseVendorsDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除供应商")
    public Response<String> adminDeleteVendor(@PathVariable Long id) {
        vendorsService.adminDeleteVendor(id);
        return Response.success("删除供应商成功");
    }

    @PostMapping("/")
    @Operation(summary = "创建供应商")
    public Response<BaseVendorsVO> adminCreateVendor(@RequestBody BaseVendorsDTO baseVendorsDTO) {
        return Response.success("创建供应商成功", vendorsService.adminCreateVendor(baseVendorsDTO));
    }
}
