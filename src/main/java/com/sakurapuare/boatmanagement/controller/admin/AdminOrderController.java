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

    @PostMapping("goods/list")
    @Operation(summary = "获取商家订单列表")
    public Response<List<BaseBoatOrdersVO>> getAdminGoodsOrdersListQuery(@RequestBody BaseBoatOrdersDTO baseBoatOrderDTO) {
        List<BaseBoatOrdersVO> adminOrders = boatOrdersService.getAdminGoodsOrdersListQuery(baseBoatOrderDTO);
        return Response.success("获取商家订单列表成功", adminOrders);
    }

    @PostMapping("goods/page")
    @Operation(summary = "获取商家订单列表分页")
    public Response<Page<BaseBoatOrdersVO>> getAdminGoodsOrdersPageQuery(@RequestParam(defaultValue = "1") Integer pageNum,
                                                                         @RequestParam(defaultValue = "10") Integer pageSize,
                                                                         @RequestBody BaseBoatOrdersDTO baseBoatOrderDTO) {
        return Response.success("获取商家订单列表分页成功", boatOrdersService.getAdminGoodsOrdersPageQuery(pageNum, pageSize, baseBoatOrderDTO));
    }

    @PostMapping("boat/list")
    @Operation(summary = "获取船舶订单列表")
    public Response<List<BaseBoatOrdersVO>> getAdminBoatOrdersListQuery(@RequestBody BaseBoatOrdersDTO baseBoatOrderDTO) {
        List<BaseBoatOrdersVO> adminOrders = boatOrdersService.getAdminBoatOrdersListQuery(baseBoatOrderDTO);
        return Response.success("获取船舶订单列表成功", adminOrders);
    }

    @PostMapping("boat/page")
    @Operation(summary = "获取船舶订单列表分页")
    public Response<Page<BaseBoatOrdersVO>> getAdminBoatOrdersPageQuery(@RequestParam(defaultValue = "1") Integer pageNum,
                                                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                                                        @RequestBody BaseBoatOrdersDTO baseBoatOrderDTO) {
        return Response.success("获取商家订单列表分页成功", boatOrdersService.getAdminBoatOrdersPageQuery(pageNum, pageSize, baseBoatOrderDTO));
    }

}
