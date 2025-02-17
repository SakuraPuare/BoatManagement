package com.sakurapuare.boatmanagement.controller.merchant;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseGoodsOrdersDTO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseGoodsOrdersVO;
import com.sakurapuare.boatmanagement.service.GoodsOrdersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/merchant/order")
@Tag(name = "MerchantOrder", description = "商家订单模块")
@RequiredArgsConstructor
public class MerchantOrderController {

    private final GoodsOrdersService goodsOrdersService;

    @PostMapping("/list")
    @Operation(summary = "获取商家订单列表")
    public Response<List<BaseGoodsOrdersVO>> getMerchantOrdersListQuery(@RequestBody BaseGoodsOrdersDTO merchantOrderDTO) {
        List<BaseGoodsOrdersVO> merchantOrders = goodsOrdersService.getMerchantsGoodsOrdersListQuery(merchantOrderDTO);
        return Response.success(merchantOrders);
    }

    @PostMapping("/page")
    @Operation(summary = "获取商家订单列表分页")

    public Response<Page<BaseGoodsOrdersVO>> getMerchantOrdersPageQuery(@RequestParam(defaultValue = "1") Integer pageNum,
                                                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                                                   @RequestBody BaseGoodsOrdersDTO merchantOrderDTO) {
        Page<BaseGoodsOrdersVO> merchantOrders = goodsOrdersService.getMerchantsGoodsOrdersPageQuery(pageNum, pageSize,
                merchantOrderDTO);
        return Response.success(merchantOrders);
    }

    @PostMapping("/complete/{id}")
    @Operation(summary = "完成订单")
    public Response<String> completeOrder(@PathVariable Long id) {
        goodsOrdersService.completeMerchantOrder(id);
        return Response.success("订单完成");
    }

    @PostMapping("/cancel/{id}")
    @Operation(summary = "取消订单")
    public Response<String> cancelOrder(@PathVariable Long id) {
        goodsOrdersService.cancelMerchantOrder(id);
        return Response.success("订单取消");
    }
}
