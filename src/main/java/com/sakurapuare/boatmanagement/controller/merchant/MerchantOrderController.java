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

    /*
     * 商品订单管理
     */

    @PostMapping("/goods/list")
    @Operation(summary = "获取商家商品订单列表")
    public Response<List<BaseGoodsOrdersVO>> merchantGetGoodsOrdersList(@RequestBody BaseGoodsOrdersDTO merchantOrderDTO) {
        List<BaseGoodsOrdersVO> merchantOrders = goodsOrdersService.merchantGetGoodsOrdersList(merchantOrderDTO);
        return Response.success(merchantOrders);
    }

    @PostMapping("/goods/page")
    @Operation(summary = "获取商家商品订单列表分页")
    public Response<Page<BaseGoodsOrdersVO>> merchantGetGoodsOrdersPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody BaseGoodsOrdersDTO merchantOrderDTO) {
        Page<BaseGoodsOrdersVO> merchantOrders = goodsOrdersService.merchantGetGoodsOrdersPage(pageNum, pageSize,
                search, sort, startDateTime, endDateTime, merchantOrderDTO);
        return Response.success(merchantOrders);
    }

    @PostMapping("/goods/complete/{id}")
    @Operation(summary = "完成商品订单")
    public Response<String> merchantCompleteGoodsOrder(@PathVariable Long id) {
        goodsOrdersService.merchantCompleteOrder(id);
        return Response.success("商品订单完成");
    }

    @PostMapping("/goods/cancel/{id}")
    @Operation(summary = "取消商品订单")
    public Response<String> merchantCancelGoodsOrder(@PathVariable Long id) {
        goodsOrdersService.merchantCancelOrder(id);
        return Response.success("商品订单取消");
    }

    /*
     * 兼容性API（保持向后兼容）
     */

    @PostMapping("/list")
    @Operation(summary = "获取商家订单列表（兼容性API，默认返回商品订单）")
    public Response<List<BaseGoodsOrdersVO>> merchantGetOrdersList(@RequestBody BaseGoodsOrdersDTO merchantOrderDTO) {
        return merchantGetGoodsOrdersList(merchantOrderDTO);
    }

    @PostMapping("/page")
    @Operation(summary = "获取商家订单列表分页（兼容性API，默认返回商品订单）")
    public Response<Page<BaseGoodsOrdersVO>> merchantGetOrdersPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestBody BaseGoodsOrdersDTO merchantOrderDTO) {
        return merchantGetGoodsOrdersPage(pageNum, pageSize, null, null, null, null, merchantOrderDTO);
    }

    @PostMapping("/complete/{id}")
    @Operation(summary = "完成订单（兼容性API，默认处理商品订单）")
    public Response<String> merchantCompleteOrder(@PathVariable Long id) {
        return merchantCompleteGoodsOrder(id);
    }

    @PostMapping("/cancel/{id}")
    @Operation(summary = "取消订单（兼容性API，默认处理商品订单）")
    public Response<String> merchantCancelOrder(@PathVariable Long id) {
        return merchantCancelGoodsOrder(id);
    }
}
