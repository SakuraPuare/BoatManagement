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
@Tag(name = "商家-商品模块", description = "商家商品模块")
@RequiredArgsConstructor
public class MerchantGoodsController {

    private final GoodsService goodsService;


    @GetMapping("/list")
    @Operation(summary = "获取商家商品列表")
    public Response<List<BaseGoodsVO>> getGoods() {
        List<BaseGoodsVO> goods = goodsService.getMerchantsGoodsList();
        return Response.success(goods);
    }

    @GetMapping("/page")
    @Operation(summary = "获取商家商品列表分页")
    public Response<Page<BaseGoodsVO>> getGoodsPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        Page<BaseGoodsVO> page = goodsService.getMerchantsGoodsPage(pageNum, pageSize);
        return Response.success(page);
    }

    @PostMapping("/")
    @Operation(summary = "添加商品")
    public Response<String> addGoods(@RequestBody BaseGoodsDTO goods) {
        goodsService.addGoods(goods);
        return Response.success("添加成功");
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新商品")
    public Response<BaseGoodsVO> updateGoods(@PathVariable Long id, @RequestBody BaseGoodsDTO goods) {
        goodsService.updateGoods(id, goods);
        return Response.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除商品")
    public Response<BaseGoodsVO> deleteGoods(@PathVariable Long id) {
        goodsService.deleteGoods(id);
        return Response.success();
    }
}
