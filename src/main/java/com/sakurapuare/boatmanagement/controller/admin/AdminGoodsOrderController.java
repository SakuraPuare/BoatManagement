package com.sakurapuare.boatmanagement.controller.admin;

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
@RequestMapping("/admin/goods/order")
@Tag(name = "AdminGoodsOrder", description = "管理员商品订单模块")
@RequiredArgsConstructor
public class AdminGoodsOrderController {

    private final GoodsOrdersService goodsOrdersService;

    @GetMapping("/list")
    @Operation(summary = "获取商品订单列表")
    public Response<List<BaseGoodsOrdersVO>> getGoodsOrdersList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseGoodsOrdersDTO filter) {
        List<BaseGoodsOrdersVO> orders = goodsOrdersService.adminGetGoodsOrdersList(search, sort, startDateTime, endDateTime, filter);
        return Response.success("获取商品订单列表成功", orders);
    }

    @GetMapping("/page")
    @Operation(summary = "分页获取商品订单列表")
    public Response<Page<BaseGoodsOrdersVO>> getGoodsOrdersPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseGoodsOrdersDTO filter) {
        Page<BaseGoodsOrdersVO> orderPage = goodsOrdersService.adminGetGoodsOrdersPage(pageNum, pageSize, search, sort, startDateTime, endDateTime, filter);
        return Response.success("分页获取商品订单列表成功", orderPage);
    }

    @GetMapping("/ids")
    @Operation(summary = "根据 ID 获取商品订单")
    public Response<List<BaseGoodsOrdersVO>> getGoodsOrdersByIds(@RequestParam String ids) {
        List<BaseGoodsOrdersVO> orders = goodsOrdersService.adminGetGoodsOrdersByIds(ids);
        return Response.success("根据 ID 获取商品订单成功", orders);
    }

    @PutMapping("/{id}/complete")
    @Operation(summary = "完成商品订单")
    public Response<Void> completeOrder(@PathVariable Long id) {
        goodsOrdersService.adminCompleteOrder(id);
        return Response.success("完成商品订单成功", null);
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "取消商品订单")
    public Response<Void> cancelOrder(@PathVariable Long id) {
        goodsOrdersService.adminCancelOrder(id);
        return Response.success("取消商品订单成功", null);
    }
} 