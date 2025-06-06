package com.sakurapuare.boatmanagement.controller.merchant;

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
@RequestMapping("/merchant/goods")
@Tag(name = "MerchantGoods", description = "商家商品模块")
@RequiredArgsConstructor
public class MerchantGoodsController {

    private final GoodsService goodsService;

    @PostMapping("/list")
    @Operation(summary = "获取商家商品列表")
    public Response<List<BaseGoodsVO>> merchantGetGoodsList(@RequestBody BaseGoodsDTO queryDTO) {
        List<BaseGoodsVO> goods = goodsService.merchantGetGoodsListQuery(queryDTO);
        return Response.success(goods);
    }

    @PostMapping("/page")
    @Operation(summary = "获取商家商品列表分页")
    public Response<Page<BaseGoodsVO>> merchantGetGoodsPage(
            @RequestParam(defaultValue = "1") Integer pageNum, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestBody BaseGoodsDTO queryDTO) {
        Page<BaseGoodsVO> page = goodsService.merchantGetGoodsPageQuery(pageNum, pageSize, queryDTO);
        return Response.success(page);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取商品详情")
    public Response<BaseGoodsVO> merchantGetGoods(@PathVariable Long id) {
        BaseGoodsVO goods = goodsService.merchantGetGoodsById(id);
        return Response.success(goods);
    }

    @PostMapping("/")
    @Operation(summary = "添加商品")
    public Response<String> merchantCreateGoods(@RequestBody BaseGoodsDTO goods) {
        goodsService.merchantCreateGoods(goods);
        return Response.success("添加商品成功");
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新商品")
    public Response<String> merchantUpdateGoods(@PathVariable Long id, @RequestBody BaseGoodsDTO goods) {
        goodsService.merchantUpdateGoods(id, goods);
        return Response.success("更新商品成功");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除商品")
    public Response<String> merchantDeleteGoods(@PathVariable Long id) {
        goodsService.merchantDeleteGoods(id);
        return Response.success("删除商品成功");
    }
}
