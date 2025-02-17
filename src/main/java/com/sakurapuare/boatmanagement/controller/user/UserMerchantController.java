package com.sakurapuare.boatmanagement.controller.user;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseGoodsDTO;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseGoodsOrdersDTO;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseMerchantsDTO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseGoodsVO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseMerchantsVO;
import com.sakurapuare.boatmanagement.service.GoodsService;
import com.sakurapuare.boatmanagement.service.MerchantsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/merchant")
@Tag(name = "UserMerchant", description = "用户商家模块")
@RequiredArgsConstructor
public class UserMerchantController {

    private final MerchantsService merchantService;

    private final GoodsService goodsService;

    @PostMapping("/list")
    @Operation(summary = "获取所有商家列表")
    public Response<List<BaseMerchantsVO>> getUserMerchantListQuery(@RequestBody BaseMerchantsDTO queryDTO) {
        return Response.success(merchantService.getUserMerchantListQuery(queryDTO));
    }

    @PostMapping("/page")
    @Operation(summary = "获取所有商家列表分页")
    public Response<Page<BaseMerchantsVO>> getUserMerchantPageQuery(@RequestParam(defaultValue = "1") Integer pageNum,
                                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                                            @RequestBody BaseMerchantsDTO queryDTO) {
        return Response.success(merchantService.getUserMerchantPageQuery(pageNum, pageSize, queryDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取商家详情")
    public Response<BaseMerchantsVO> getUserMerchantById(@PathVariable Long id) {
        return Response.success(merchantService.getUserMerchantById(id));
    }

    @PostMapping("/{merchantId}/goods")
    @Operation(summary = "获取商家商品列表")
    public Response<List<BaseGoodsVO>> getUserMerchantGoodsList(@PathVariable Long merchantId,
                                                                @RequestBody BaseGoodsDTO queryDTO) {
        return Response.success(goodsService.getUserMerchantGoodsList(merchantId, queryDTO));
    }

    @PostMapping("/{merchantId}/goods/page")
    @Operation(summary = "获取商家商品列表分页")
    public Response<Page<BaseGoodsVO>> getUserMerchantGoodsPage(@PathVariable Long merchantId,
                                                                @RequestParam(defaultValue = "1") Integer pageNum,
                                                                @RequestParam(defaultValue = "10") Integer pageSize,
                                                                @RequestBody BaseGoodsDTO queryDTO) {
        return Response.success(goodsService.getUserMerchantGoodsPage(merchantId, pageNum, pageSize, queryDTO));
    }

    @PostMapping("/{merchantId}/goods/order")
    @Operation(summary = "创建商家商品订单")
    public Response<String> createUserMerchantGoodsOrder(@PathVariable Long merchantId,
                                                        @RequestBody BaseGoodsOrdersDTO orderDTO) {
        return Response.success(goodsService.createUserMerchantGoodsOrder(merchantId, orderDTO));
    }
    
}