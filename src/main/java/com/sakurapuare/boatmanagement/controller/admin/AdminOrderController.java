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
@Tag(name = "AdminOrder", description = "管理员订单管理模块")
@RequiredArgsConstructor
public class AdminOrderController {

    private final BoatOrdersService boatOrdersService;

    @PostMapping("goods/list")
    @Operation(summary = "获取商品订单列表")
    public Response<List<BaseBoatOrdersVO>> adminGetGoodsOrdersList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseBoatOrdersDTO filter) {
        return Response.success("获取商品订单列表成功", 
                boatOrdersService.adminGetGoodsOrdersList(search, sort, startDateTime, endDateTime, filter));
    }

    @PostMapping("goods/page")
    @Operation(summary = "分页获取商品订单列表")
    public Response<Page<BaseBoatOrdersVO>> adminGetGoodsOrdersPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseBoatOrdersDTO filter) {
        return Response.success("分页获取商品订单列表成功", 
                boatOrdersService.adminGetGoodsOrdersPage(pageNum, pageSize, search, sort, startDateTime, endDateTime, filter));
    }

    @GetMapping("goods/ids")
    @Operation(summary = "根据 ID 获取商品订单")
    public Response<List<BaseBoatOrdersVO>> adminGetGoodsOrdersByIds(@RequestParam String ids) {
        return Response.success("根据 ID 获取商品订单成功", boatOrdersService.adminGetGoodsOrdersByIds(ids));
    }

    @PostMapping("boat/list")
    @Operation(summary = "获取船舶订单列表")
    public Response<List<BaseBoatOrdersVO>> adminGetBoatOrdersList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseBoatOrdersDTO filter) {
        return Response.success("获取船舶订单列表成功", 
                boatOrdersService.adminGetBoatOrdersList(search, sort, startDateTime, endDateTime, filter));
    }

    @PostMapping("boat/page")
    @Operation(summary = "分页获取船舶订单列表")
    public Response<Page<BaseBoatOrdersVO>> adminGetBoatOrdersPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseBoatOrdersDTO filter) {
        return Response.success("分页获取船舶订单列表成功", 
                boatOrdersService.adminGetBoatOrdersPage(pageNum, pageSize, search, sort, startDateTime, endDateTime, filter));
    }

    @GetMapping("boat/ids")
    @Operation(summary = "根据 ID 获取船舶订单")
    public Response<List<BaseBoatOrdersVO>> adminGetBoatOrdersByIds(@RequestParam String ids) {
        return Response.success("根据 ID 获取船舶订单成功", boatOrdersService.adminGetBoatOrdersByIds(ids));
    }
}
