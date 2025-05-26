package com.sakurapuare.boatmanagement.controller;

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
@RequestMapping("/public/goods")
@Tag(name = "PublicGoods", description = "公共商品模块")
@RequiredArgsConstructor
public class PublicGoodsController {

    private final GoodsService goodsService;

    @PostMapping("/list")
    @Operation(summary = "获取商品列表")
    public Response<List<BaseGoodsVO>> getPublicGoodsList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseGoodsDTO filter) {
        return Response.success("获取商品列表成功", 
                goodsService.publicGetGoodsList(search, sort, startDateTime, endDateTime, filter));
    }

    @PostMapping("/page")
    @Operation(summary = "分页获取商品列表")
    public Response<Page<BaseGoodsVO>> getPublicGoodsPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseGoodsDTO filter) {
        return Response.success("获取商品列表分页成功", 
                goodsService.publicGetGoodsPage(pageNum, pageSize, search, sort, startDateTime, endDateTime, filter));
    }

    @GetMapping("/ids")
    @Operation(summary = "根据 ID 获取商品列表")
    public Response<List<BaseGoodsVO>> getPublicGoodsByIds(@RequestParam String ids) {
        return Response.success("根据 ID 获取商品列表成功", goodsService.publicGetGoodsByIds(ids));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取商品详情")
    public Response<BaseGoodsVO> getPublicGoods(@PathVariable Long id) {
        return Response.success("获取商品详情成功", goodsService.publicGetGoodsByIds(id.toString()).get(0));
    }
} 