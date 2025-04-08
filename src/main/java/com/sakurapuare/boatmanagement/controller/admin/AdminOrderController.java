package com.sakurapuare.boatmanagement.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseBoatOrdersDTO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseBoatOrdersVO;
import com.sakurapuare.boatmanagement.service.BoatOrdersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/order")
@Tag(name = "AdminOrder", description = "商家订单模块")
@RequiredArgsConstructor
public class AdminOrderController {

    private final BoatOrdersService boatOrdersService;

    @GetMapping("goods/list")
    @Operation(summary = "获取商品订单列表")
    public Response<List<BaseBoatOrdersVO>> getGoodsOrdersList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseBoatOrdersDTO filter) {
        List<BaseBoatOrdersVO> orders = boatOrdersService.adminGetGoodsOrdersList(search, sort, startDateTime, endDateTime, filter);
        return Response.success("获取商品订单列表成功", orders);
    }

    @GetMapping("goods/page")
    @Operation(summary = "分页获取商品订单列表")
    public Response<Page<BaseBoatOrdersVO>> getGoodsOrdersPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseBoatOrdersDTO filter) {
        Page<BaseBoatOrdersVO> orderPage = boatOrdersService.adminGetGoodsOrdersPage(pageNum, pageSize, search, sort, startDateTime, endDateTime, filter);
        return Response.success("分页获取商品订单列表成功", orderPage);
    }

    @GetMapping("goods/ids")
    @Operation(summary = "根据ID获取商品订单")
    public Response<List<BaseBoatOrdersVO>> getGoodsOrdersByIds(@RequestParam String ids) {
        List<BaseBoatOrdersVO> orders = boatOrdersService.adminGetGoodsOrdersByIds(ids);
        return Response.success("根据ID获取商品订单成功", orders);
    }

    @GetMapping("boat/list")
    @Operation(summary = "获取船舶订单列表")
    public Response<List<BaseBoatOrdersVO>> getBoatOrdersList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseBoatOrdersDTO filter) {
        List<BaseBoatOrdersVO> orders = boatOrdersService.adminGetBoatOrdersList(search, sort, startDateTime, endDateTime, filter);
        return Response.success("获取船舶订单列表成功", orders);
    }

    @GetMapping("boat/page")
    @Operation(summary = "分页获取船舶订单列表")
    public Response<Page<BaseBoatOrdersVO>> getBoatOrdersPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseBoatOrdersDTO filter) {
        Page<BaseBoatOrdersVO> orderPage = boatOrdersService.adminGetBoatOrdersPage(pageNum, pageSize, search, sort, startDateTime, endDateTime, filter);
        return Response.success("分页获取船舶订单列表成功", orderPage);
    }

    @GetMapping("boat/ids")
    @Operation(summary = "根据ID获取船舶订单")
    public Response<List<BaseBoatOrdersVO>> getBoatOrdersByIds(@RequestParam String ids) {
        List<BaseBoatOrdersVO> orders = boatOrdersService.adminGetBoatOrdersByIds(ids);
        return Response.success("根据ID获取船舶订单成功", orders);
    }
}
