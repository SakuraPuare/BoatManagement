package com.sakurapuare.boatmanagement.controller.user;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseBoatOrdersDTO;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseGoodsOrdersDTO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseBoatOrdersVO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseGoodsOrdersVO;
import com.sakurapuare.boatmanagement.service.BoatOrdersService;
import com.sakurapuare.boatmanagement.service.GoodsOrdersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/order")
@Tag(name = "UserOrder", description = "用户订单模块")
@RequiredArgsConstructor
public class UserOrderController {

    private final GoodsOrdersService goodsOrdersService;

    private final BoatOrdersService boatOrdersService;

    /*
     * 商品订单
     */

    @PostMapping("/goods/list")
    @Operation(summary = "获取用户商品订单列表")
    public Response<List<BaseGoodsOrdersVO>> getUserGoodsOrdersListQuery(
            @RequestBody BaseGoodsOrdersDTO goodsOrdersDTO) {
        return Response.success("获取用户商品订单列表成功", goodsOrdersService.getUserGoodsOrdersListQuery(goodsOrdersDTO));
    }

    @PostMapping("/goods/page")
    @Operation(summary = "获取用户商品订单列表分页")
    public Response<Page<BaseGoodsOrdersVO>> getUserGoodsOrdersPageQuery(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestBody BaseGoodsOrdersDTO goodsOrdersDTO) {
        return Response.success("获取用户商品订单列表分页成功",
                goodsOrdersService.getUserGoodsOrdersPageQuery(pageNum, pageSize, goodsOrdersDTO));
    }

    @PostMapping("/goods/pay/{id}")
    @Operation(summary = "支付用户商品订单")
    public Response<String> payUserGoodsOrder(@PathVariable Long id) {
        goodsOrdersService.payUserGoodsOrder(id);
        return Response.success("支付用户商品订单成功");
    }

    @PostMapping("/goods/cancel/{id}")
    @Operation(summary = "取消用户商品订单")
    public Response<String> cancelUserGoodsOrder(@PathVariable Long id) {
        goodsOrdersService.cancelUserGoodsOrder(id);
        return Response.success("取消用户商品订单成功");
    }

    /*
     * 船舶订单
     */

    @PostMapping("/boat/list")
    @Operation(summary = "获取用户船舶订单列表")
    public Response<List<BaseBoatOrdersVO>> getUserBoatOrdersListQuery(@RequestBody BaseBoatOrdersDTO boatOrdersDTO) {
        return Response.success("获取用户船舶订单列表成功", boatOrdersService.getUserBoatOrdersListQuery(boatOrdersDTO));
    }

    @PostMapping("/boat/page")
    @Operation(summary = "获取用户船舶订单列表分页")
    public Response<Page<BaseBoatOrdersVO>> getUserBoatOrdersPageQuery(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestBody BaseBoatOrdersDTO boatOrdersDTO) {
        return Response.success("获取用户船舶订单列表分页成功",
                boatOrdersService.getUserBoatOrdersPageQuery(pageNum, pageSize, boatOrdersDTO));
    }

    @PostMapping("/boat/pay/{id}")
    @Operation(summary = "支付用户船舶订单")
    public Response<String> payUserBoatOrders(@PathVariable Long id) {
        boatOrdersService.payUserBoatOrders(id);
        return Response.success("支付用户船舶订单成功");
    }

    @PostMapping("/boat/cancel/{id}")
    @Operation(summary = "取消用户船舶订单")
    public Response<String> cancelUserBoatOrder(@PathVariable Long id) {
        boatOrdersService.cancelUserBoatOrders(id);
        return Response.success("取消用户船舶订单成功");
    }
}
