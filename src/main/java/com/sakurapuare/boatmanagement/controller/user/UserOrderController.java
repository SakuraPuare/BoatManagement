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

    @GetMapping("/goods/list")
    @Operation(summary = "获取用户商品订单列表")
    public Response<List<BaseGoodsOrdersVO>> userGetGoodsOrdersList(
            @RequestBody BaseGoodsOrdersDTO goodsOrdersDTO) {
        return Response.success(goodsOrdersService.userGetGoodsOrdersList(goodsOrdersDTO));
    }

    @GetMapping("/goods/page")
    @Operation(summary = "获取用户商品订单列表分页")
    public Response<Page<BaseGoodsOrdersVO>> userGetGoodsOrdersPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestBody BaseGoodsOrdersDTO goodsOrdersDTO) {
        return Response.success(goodsOrdersService.userGetGoodsOrdersPage(pageNum, pageSize, goodsOrdersDTO));
    }

    @PutMapping("/goods/pay/{id}")
    @Operation(summary = "支付用户商品订单")
    public Response<Void> userPayGoodsOrder(@PathVariable Long id) {
        goodsOrdersService.userPayGoodsOrder(id);
        return Response.success();
    }

    @PutMapping("/goods/cancel/{id}")
    @Operation(summary = "取消用户商品订单")
    public Response<Void> userCancelGoodsOrder(@PathVariable Long id) {
        goodsOrdersService.userCancelGoodsOrder(id);
        return Response.success();
    }

    /*
     * 船舶订单
     */

    @GetMapping("/boat/list")
    @Operation(summary = "获取用户船舶订单列表")
    public Response<List<BaseBoatOrdersVO>> userGetBoatOrdersList(
            @RequestBody BaseBoatOrdersDTO boatOrdersDTO) {
        return Response.success(boatOrdersService.getUserBoatOrdersListQuery(boatOrdersDTO));
    }

    @GetMapping("/boat/page")
    @Operation(summary = "获取用户船舶订单列表分页")
    public Response<Page<BaseBoatOrdersVO>> userGetBoatOrdersPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestBody BaseBoatOrdersDTO boatOrdersDTO) {
        return Response.success(boatOrdersService.getUserBoatOrdersPageQuery(pageNum, pageSize, boatOrdersDTO));
    }

    @PutMapping("/boat/pay/{id}")
    @Operation(summary = "支付用户船舶订单")
    public Response<Void> userPayBoatOrder(@PathVariable Long id) {
        boatOrdersService.payUserBoatOrders(id);
        return Response.success();
    }

    @PutMapping("/boat/cancel/{id}")
    @Operation(summary = "取消用户船舶订单")
    public Response<Void> userCancelBoatOrder(@PathVariable Long id) {
        boatOrdersService.cancelUserBoatOrders(id);
        return Response.success();
    }
}
