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
@Tag(name = "AdminMerchant", description = "商户模块")
@RequiredArgsConstructor
public class AdminMerchantController {

    private final MerchantsService merchantService;

    @GetMapping("/list")
    @Operation(summary = "获取商户列表")
    public Response<List<BaseMerchantsVO>> getMerchantList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseMerchantsDTO filter) {
        List<BaseMerchantsVO> merchants = merchantService.adminGetMerchantList(search, sort, startDateTime, endDateTime, filter);
        return Response.success("获取商户列表成功", merchants);
    }

    @GetMapping("/page")
    @Operation(summary = "分页获取商户列表")
    public Response<Page<BaseMerchantsVO>> getMerchantPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseMerchantsDTO filter) {
        Page<BaseMerchantsVO> merchantPage = merchantService.adminGetMerchantPage(pageNum, pageSize, search, sort, startDateTime, endDateTime, filter);
        return Response.success("分页获取商户列表成功", merchantPage);
    }

    @GetMapping("/ids")
    @Operation(summary = "根据ID获取商户")
    public Response<List<BaseMerchantsVO>> getMerchantByIds(@RequestParam String ids) {
        List<BaseMerchantsVO> merchants = merchantService.adminGetMerchantByIds(ids);
        return Response.success("根据ID获取商户成功", merchants);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取商户详情")
    public Response<BaseMerchantsVO> getMerchantById(@PathVariable Long id) {
        BaseMerchantsVO merchant = merchantService.adminGetMerchantById(id);
        return Response.success("获取商户详情成功", merchant);
    }

    @PostMapping
    @Operation(summary = "创建商户")
    public Response<BaseMerchantsVO> createMerchant(@RequestBody BaseMerchantsDTO dto) {
        BaseMerchantsVO merchant = merchantService.adminCreateMerchant(dto);
        return Response.success("创建商户成功", merchant);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新商户")
    public Response<BaseMerchantsVO> updateMerchant(@PathVariable Long id, @RequestBody BaseMerchantsDTO dto) {
        BaseMerchantsVO merchant = merchantService.adminUpdateMerchant(id, dto);
        return Response.success("更新商户成功", merchant);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除商户")
    public Response<Void> deleteMerchant(@PathVariable Long id) {
        merchantService.adminDeleteMerchant(id);
        return Response.success("删除商户成功", null);
    }
}
