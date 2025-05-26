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

    @PostMapping("/list")
    @Operation(summary = "获取商品订单列表")
    public Response<List<BaseGoodsOrdersVO>> adminGetGoodsOrdersList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseGoodsOrdersDTO filter) {
        return Response.success("获取商品订单列表成功", 
                goodsOrdersService.adminGetGoodsOrdersList(search, sort, startDateTime, endDateTime, filter));
    }

    @PostMapping("/page")
    @Operation(summary = "分页获取商品订单列表")
    public Response<Page<BaseGoodsOrdersVO>> adminGetGoodsOrdersPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseGoodsOrdersDTO filter) {
        return Response.success("分页获取商品订单列表成功", 
                goodsOrdersService.adminGetGoodsOrdersPage(pageNum, pageSize, search, sort, startDateTime, endDateTime, filter));
    }

    @GetMapping("/ids")
    @Operation(summary = "根据 ID 获取商品订单")
    public Response<List<BaseGoodsOrdersVO>> adminGetGoodsOrdersByIds(@RequestParam String ids) {
        return Response.success("根据 ID 获取商品订单成功", goodsOrdersService.adminGetGoodsOrdersByIds(ids));
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