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
@Tag(name = "管理员-商户模块", description = "商户模块")
@RequiredArgsConstructor
public class AdminMerchantController {

    private final MerchantsService merchantService;

    @GetMapping("/list")
    @Operation(summary = "获取商户列表")
    public Response<List<BaseMerchantsVO>> list(@RequestParam BaseMerchantsDTO queryDTO) {
        return Response.success("获取商户列表成功", merchantService.getListQuery(queryDTO));
    }

    @GetMapping("/page")
    @Operation(summary = "获取商户列表分页")
    public Response<Page<BaseMerchantsVO>> listPage(@RequestParam(defaultValue = "1") Integer page,
                                                    @RequestParam(defaultValue = "10") Integer size, @RequestParam BaseMerchantsDTO queryDTO) {
        return Response.success("获取商户列表分页成功", merchantService.getPageQuery(page, size, queryDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取商户详情")
    public Response<BaseMerchantsVO> get(@PathVariable Long id) {
        Merchants merchants = merchantService.getById(id);
        BaseMerchantsVO merchantsVO = new BaseMerchantsVO();
        BeanUtils.copyProperties(merchants, merchantsVO);
        return Response.success("获取商户详情成功", merchantsVO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新商户")
    public Response<String> update(@PathVariable Long id, @RequestBody BaseMerchantsDTO baseMerchantsDTO) {
        merchantService.updateMerchant(id, baseMerchantsDTO);
        return Response.success("更新商户成功");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除商户")
    public Response<String> delete(@PathVariable Long id) {
        merchantService.deleteMerchant(id);
        return Response.success("删除商户成功");
    }

    @PostMapping("/")
    @Operation(summary = "创建商户")
    public Response<String> create(@RequestBody BaseMerchantsDTO baseMerchantsDTO) {
        merchantService.addMerchant(baseMerchantsDTO);
        return Response.success("创建商户成功");
    }
}
