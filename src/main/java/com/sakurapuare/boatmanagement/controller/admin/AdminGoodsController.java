package com.sakurapuare.boatmanagement.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseGoodsDTO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseGoodsVO;
import com.sakurapuare.boatmanagement.service.GoodsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/goods")
@Tag(name = "AdminGoods", description = "管理员商品管理模块")
@RequiredArgsConstructor
public class AdminGoodsController {

    private final GoodsService goodsService;

    @PostMapping("/list")
    @Operation(summary = "获取商品列表")
    public Response<List<BaseGoodsVO>> adminGetGoodsList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseGoodsDTO filter) {
        return Response.success("获取商品列表成功", 
                goodsService.adminGetGoodsList(search, sort, startDateTime, endDateTime, filter));
    }

    @PostMapping("/page")
    @Operation(summary = "分页获取商品列表")
    public Response<Page<BaseGoodsVO>> adminGetGoodsPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseGoodsDTO filter) {
        return Response.success("获取商品列表分页成功", 
                goodsService.adminGetGoodsPage(pageNum, pageSize, search, sort, startDateTime, endDateTime, filter));
    }

    @GetMapping("/ids")
    @Operation(summary = "根据 ID 获取商品列表")
    public Response<List<BaseGoodsVO>> adminGetGoodsByIds(@RequestParam String ids) {
        return Response.success("根据 ID 获取商品列表成功", goodsService.adminGetGoodsByIds(ids));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取商品详情")
    public Response<BaseGoodsVO> adminGetGoods(@PathVariable Long id) {
        return Response.success("获取商品详情成功", goodsService.adminGetGoodsByIds(id.toString()).get(0));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新商品")
    public Response<BaseGoodsVO> adminUpdateGoods(@PathVariable Long id, @RequestBody BaseGoodsDTO baseGoodsDTO) {
        return Response.success("更新商品成功", goodsService.adminUpdateGoods(id, baseGoodsDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除商品")
    public Response<String> adminDeleteGoods(@PathVariable Long id) {
        goodsService.adminDeleteGoods(id);
        return Response.success("删除商品成功");
    }

    @PostMapping("/")
    @Operation(summary = "创建商品")
    public Response<BaseGoodsVO> adminCreateGoods(
            @RequestBody BaseGoodsDTO baseGoodsDTO,
            @RequestParam Long merchantId,
            @RequestParam Long unitId) {
        return Response.success("创建商品成功", goodsService.adminCreateGoods(baseGoodsDTO, merchantId, unitId));
    }
} 