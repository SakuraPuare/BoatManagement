package com.sakurapuare.boatmanagement.controller.vendor;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseBoatsDTO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseBoatsVO;
import com.sakurapuare.boatmanagement.service.BoatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendor/boat")
@Tag(name = "VendorBoat", description = "船舶模块")
@RequiredArgsConstructor
public class VendorBoatController {

    private final BoatsService boatsService;

    @PostMapping("/list")
    @Operation(summary = "获取供应商船舶列表")
    public Response<List<BaseBoatsVO>> vendorGetBoatList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseBoatsDTO filter) {
        return Response.success("获取供应商船舶列表成功", boatsService.vendorGetBoatList(search, sort, startDateTime, endDateTime, filter));
    }

    @PostMapping("/page")
    @Operation(summary = "分页获取供应商船舶列表")
    public Response<Page<BaseBoatsVO>> vendorGetBoatPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseBoatsDTO filter) {
        return Response.success("获取供应商船舶列表分页成功", 
                boatsService.vendorGetBoatPage(pageNum, pageSize, search, sort, startDateTime, endDateTime, filter));
    }

    @GetMapping("/ids")
    @Operation(summary = "根据ID获取供应商船舶列表")
    public Response<List<BaseBoatsVO>> vendorGetBoatByIds(@RequestParam String ids) {
        return Response.success("根据ID获取供应商船舶列表成功", boatsService.vendorGetBoatByIds(ids));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取供应商船舶详情")
    public Response<BaseBoatsVO> vendorGetBoat(@PathVariable Long id) {
        return Response.success("获取供应商船舶详情成功", boatsService.vendorGetBoatByIds(id.toString()).get(0));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新供应商船舶")
    public Response<BaseBoatsVO> vendorUpdateBoat(@PathVariable Long id, @RequestBody BaseBoatsDTO baseBoatsDTO) {
        return Response.success("更新供应商船舶成功", boatsService.vendorUpdateBoat(id, baseBoatsDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除供应商船舶")
    public Response<String> vendorDeleteBoat(@PathVariable Long id) {
        boatsService.vendorDeleteBoat(id);
        return Response.success("删除供应商船舶成功");
    }

    @PostMapping("/")
    @Operation(summary = "创建供应商船舶")
    public Response<BaseBoatsVO> vendorCreateBoat(@RequestBody BaseBoatsDTO baseBoatsDTO) {
        return Response.success("创建供应商船舶成功", boatsService.vendorCreateBoat(baseBoatsDTO));
    }
}
