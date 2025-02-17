package com.sakurapuare.boatmanagement.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseMerchantsDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Merchants;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseMerchantsVO;
import com.sakurapuare.boatmanagement.service.MerchantsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/merchant")
@Tag(name = "AdminMerchant", description = "商户模块")
@RequiredArgsConstructor
public class AdminMerchantController {

    private final MerchantsService merchantService;

    @PostMapping("/list")
    @Operation(summary = "获取商户列表")
    public Response<List<BaseMerchantsVO>> getAdminMerchantListQuery(@RequestBody BaseMerchantsDTO queryDTO) {
        return Response.success("获取商户列表成功", merchantService.getAdminMerchantListQuery(queryDTO));
    }

    @PostMapping("/page")
    @Operation(summary = "获取商户列表分页")
    public Response<Page<BaseMerchantsVO>> getAdminMerchantPageQuery(@RequestParam(defaultValue = "1") Integer pageNum,
                                                    @RequestParam(defaultValue = "10") Integer pageSize, @RequestBody BaseMerchantsDTO queryDTO) {
        return Response.success("获取商户列表分页成功", merchantService.getAdminMerchantPageQuery(pageNum, pageSize, queryDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取商户详情")
    public Response<BaseMerchantsVO> getAdminMerchant(@PathVariable Long id) {
        Merchants merchants = merchantService.getById(id);
        BaseMerchantsVO merchantsVO = new BaseMerchantsVO();
        BeanUtils.copyProperties(merchants, merchantsVO);
        return Response.success("获取商户详情成功", merchantsVO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新商户")
    public Response<String> updateAdminMerchant(@PathVariable Long id, @RequestBody BaseMerchantsDTO baseMerchantsDTO) {
        merchantService.updateAdminMerchant(id, baseMerchantsDTO);
        return Response.success("更新商户成功");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除商户")
    public Response<String> deleteAdminMerchant(@PathVariable Long id) {
        merchantService.deleteAdminMerchant(id);
        return Response.success("删除商户成功");
    }

    @PostMapping("/")
    @Operation(summary = "创建商户")
    public Response<String> createAdminMerchant(@RequestBody BaseMerchantsDTO baseMerchantsDTO) {
        merchantService.createAdminMerchant(baseMerchantsDTO);
        return Response.success("创建商户成功");
    }
}
