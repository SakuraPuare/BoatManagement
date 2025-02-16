package com.sakurapuare.boatmanagement.controller.user;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseBoatOrdersDTO;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseGoodsOrdersDTO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseBoatOrdersVO;
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

    private final BoatOrdersService boatOrdersService;

    private final GoodsOrdersService goodsOrdersService;

    @PostMapping("/boat/list")
    @Operation(summary = "获取用户船只订单列表", description = "获取用户船只订单列表")
    public Response<List<BaseBoatOrdersVO>> getBoatOrders(@RequestBody BaseBoatOrdersDTO boatOrderDTO) {
        return Response.success("获取订单成功", boatOrdersService.getUserBoatOrders(boatOrderDTO));
    }

    @PostMapping("/goods/list")
    @Operation(summary = "获取用户商品订单列表", description = "获取用户商品订单列表")
    public Response<List<BaseGoodsOrdersDTO>> getGoodsOrders(@RequestBody BaseGoodsOrdersDTO goodsOrderDTO) {
        return Response.success("获取订单成功", goodsOrdersService.getUserGoodsOrders(goodsOrderDTO));
    }

    @PostMapping("/boat/page")
    @Operation(summary = "获取用户船只订单列表分页", description = "获取用户船只订单列表分页")
    public Response<Page<BaseBoatOrdersVO>> getBoatOrdersPage(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestBody BaseBoatOrdersDTO boatOrderDTO) {
        return Response.success("获取订单成功", boatOrdersService.getUserBoatOrdersPage(pageNum, pageSize, boatOrderDTO));
    }

    @PostMapping("/goods/page")
    @Operation(summary = "获取用户商品订单列表分页", description = "获取用户商品订单列表分页")
    public Response<Page<BaseGoodsOrdersDTO>> getGoodsOrdersPage(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestBody BaseGoodsOrdersDTO goodsOrderDTO) {
        return Response.success("获取订单成功", goodsOrdersService.getUserGoodsOrdersPage(pageNum, pageSize, goodsOrderDTO));
    }

    @PostMapping("/boat/")
    @Operation(summary = "创建用户船只订单", description = "创建用户船只订单")
    public Response<String> createBoatOrder(@RequestBody BaseBoatOrdersDTO boatOrderDTO) {
        boatOrdersService.createUserBoatOrder(boatOrderDTO);
        return Response.success("创建订单成功");
    }

    @PostMapping("/goods/")
    @Operation(summary = "创建用户商品订单", description = "创建用户商品订单")
    public Response<String> createGoodsOrder(@RequestBody BaseGoodsOrdersDTO goodsOrderDTO) {
        goodsOrdersService.createUserGoodsOrder(goodsOrderDTO);
        return Response.success("创建订单成功");
    }

    @PostMapping("/boat/cancel/{id}")
    @Operation(summary = "取消用户船只订单", description = "取消用户船只订单")
    public Response<String> cancelBoatOrder(@PathVariable Long id) {
        boatOrdersService.cancelUserBoatOrder(id);
        return Response.success("取消订单成功");
    }

    @PostMapping("/goods/cancel/{id}")
    @Operation(summary = "取消用户商品订单", description = "取消用户商品订单")
    public Response<String> cancelGoodsOrder(@PathVariable Long id) {
        goodsOrdersService.cancelUserGoodsOrder(id);
        return Response.success("取消订单成功");
    }

    @PostMapping("/boat/pay/{id}")
    @Operation(summary = "支付用户船只订单", description = "支付用户船只订单")
    public Response<String> payBoatOrder(@PathVariable Long id) {
        boatOrdersService.payUserBoatOrder(id);
        return Response.success("支付订单成功");
    }

    @PostMapping("/goods/pay/{id}")
    @Operation(summary = "支付用户商品订单", description = "支付用户商品订单")
    public Response<String> payGoodsOrder(@PathVariable Long id) {
        goodsOrdersService.payUserGoodsOrder(id);
        return Response.success("支付订单成功");
    }

}
