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
@Tag(name = "AdminGoods", description = "管理员商品模块")
@RequiredArgsConstructor
public class AdminGoodsController {

    private final GoodsService goodsService;

    @GetMapping("/list")
    @Operation(summary = "获取商品列表")
    public Response<List<BaseGoodsVO>> getGoodsList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseGoodsDTO filter) {
        List<BaseGoodsVO> goods = goodsService.adminGetGoodsList(search, sort, startDateTime, endDateTime, filter);
        return Response.success("获取商品列表成功", goods);
    }

    @GetMapping("/page")
    @Operation(summary = "分页获取商品列表")
    public Response<Page<BaseGoodsVO>> getGoodsPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseGoodsDTO filter) {
        Page<BaseGoodsVO> goodsPage = goodsService.adminGetGoodsPage(pageNum, pageSize, search, sort, startDateTime, endDateTime, filter);
        return Response.success("分页获取商品列表成功", goodsPage);
    }

    @GetMapping("/ids")
    @Operation(summary = "根据ID获取商品")
    public Response<List<BaseGoodsVO>> getGoodsByIds(@RequestParam String ids) {
        List<BaseGoodsVO> goods = goodsService.adminGetGoodsByIds(ids);
        return Response.success("根据ID获取商品成功", goods);
    }

    @PostMapping
    @Operation(summary = "创建商品")
    public Response<BaseGoodsVO> createGoods(@RequestBody BaseGoodsDTO dto) {
        BaseGoodsVO goods = goodsService.adminCreateGoods(dto);
        return Response.success("创建商品成功", goods);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新商品")
    public Response<BaseGoodsVO> updateGoods(@PathVariable Long id, @RequestBody BaseGoodsDTO dto) {
        BaseGoodsVO goods = goodsService.adminUpdateGoods(id, dto);
        return Response.success("更新商品成功", goods);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除商品")
    public Response<Void> deleteGoods(@PathVariable Long id) {
        goodsService.adminDeleteGoods(id);
        return Response.success("删除商品成功", null);
    }
} 