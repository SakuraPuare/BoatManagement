package com.sakurapuare.boatmanagement.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseMerchantsDTO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseMerchantsVO;
import com.sakurapuare.boatmanagement.service.MerchantsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/merchant")
@Tag(name = "AdminMerchant", description = "管理员商户管理模块")
@RequiredArgsConstructor
public class AdminMerchantController {

    private final MerchantsService merchantsService;

    @PostMapping("/list")
    @Operation(summary = "获取商户列表")
    public Response<List<BaseMerchantsVO>> adminGetMerchantList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseMerchantsDTO filter) {
        return Response.success("获取商户列表成功", 
                merchantsService.adminGetMerchantList(search, sort, startDateTime, endDateTime, filter));
    }

    @PostMapping("/page")
    @Operation(summary = "分页获取商户列表")
    public Response<Page<BaseMerchantsVO>> adminGetMerchantPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseMerchantsDTO filter) {
        return Response.success("获取商户列表分页成功", 
                merchantsService.adminGetMerchantPage(pageNum, pageSize, search, sort, startDateTime, endDateTime, filter));
    }

    @GetMapping("/ids")
    @Operation(summary = "根据 ID 获取商户列表")
    public Response<List<BaseMerchantsVO>> adminGetMerchantByIds(@RequestParam String ids) {
        return Response.success("根据 ID 获取商户列表成功", merchantsService.adminGetMerchantByIds(ids));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取商户详情")
    public Response<BaseMerchantsVO> adminGetMerchant(@PathVariable Long id) {
        return Response.success("获取商户详情成功", merchantsService.adminGetMerchantByIds(id.toString()).get(0));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新商户")
    public Response<BaseMerchantsVO> adminUpdateMerchant(@PathVariable Long id, @RequestBody BaseMerchantsDTO baseMerchantsDTO) {
        return Response.success("更新商户成功", merchantsService.adminUpdateMerchant(id, baseMerchantsDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除商户")
    public Response<String> adminDeleteMerchant(@PathVariable Long id) {
        merchantsService.adminDeleteMerchant(id);
        return Response.success("删除商户成功");
    }

    @PostMapping("/")
    @Operation(summary = "创建商户")
    public Response<BaseMerchantsVO> adminCreateMerchant(@RequestBody BaseMerchantsDTO baseMerchantsDTO) {
        return Response.success("创建商户成功", merchantsService.adminCreateMerchant(baseMerchantsDTO));
    }
}
