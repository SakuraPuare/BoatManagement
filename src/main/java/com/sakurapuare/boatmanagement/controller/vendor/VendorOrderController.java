package com.sakurapuare.boatmanagement.controller.vendor;

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
@RequestMapping("/vendor/order")
@Tag(name = "VendorOrder", description = "商家订单模块")
@RequiredArgsConstructor
public class VendorOrderController {

    private final BoatOrdersService boatOrdersService;

    @PostMapping("/list")
    @Operation(summary = "获取商家订单列表")
    public Response<List<BaseBoatOrdersVO>> getVendorOrdersListQuery(@RequestBody BaseBoatOrdersDTO baseBoatOrderDTO) {
        List<BaseBoatOrdersVO> vendorOrders = boatOrdersService.getVendorOrdersListQuery(baseBoatOrderDTO);
        return Response.success(vendorOrders);
    }

    @PostMapping("/page")
    @Operation(summary = "获取商家订单列表分页")
    public Response<Page<BaseBoatOrdersVO>> getVendorOrdersPageQuery(@RequestParam(defaultValue = "1") Integer pageNum,
                                                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                                                     @RequestBody BaseBoatOrdersDTO baseBoatOrderDTO) {
        return Response.success(boatOrdersService.getVendorOrdersPageQuery(pageNum, pageSize, baseBoatOrderDTO));
    }

    @PostMapping("/complete/{id}")
    @Operation(summary = "完成订单")
    public Response<String> completeOrder(@PathVariable Long id) {
        boatOrdersService.completeVendorOrder(id);
        return Response.success("订单完成");
    }

    @PostMapping("/cancel/{id}")
    @Operation(summary = "取消订单")
    public Response<String> cancelOrder(@PathVariable Long id) {
        boatOrdersService.cancelVendorOrder(id);
        return Response.success("订单取消");
    }

}
