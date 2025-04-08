package com.sakurapuare.boatmanagement.controller.vendor;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseBoatTypesDTO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseBoatTypesVO;
import com.sakurapuare.boatmanagement.service.BoatTypesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendor/boat-type")
@Tag(name = "VendorBoatType", description = "船舶类型模块")
@RequiredArgsConstructor
public class VendorBoatTypeController {

    private final BoatTypesService boatTypesService;

    @PostMapping("/list")
    @Operation(summary = "获取供应商船舶类型列表")
    public Response<List<BaseBoatTypesVO>> getBoatTypeList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseBoatTypesDTO filter) {
        return Response.success("获取供应商船舶类型列表成功", 
                boatTypesService.vendorGetBoatTypeList(search, sort, startDateTime, endDateTime, filter));
    }

    @PostMapping("/page")
    @Operation(summary = "分页获取供应商船舶类型列表")
    public Response<Page<BaseBoatTypesVO>> getBoatTypePage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseBoatTypesDTO filter) {
        return Response.success("获取供应商船舶类型列表分页成功", 
                boatTypesService.vendorGetBoatTypePage(pageNum, pageSize, search, sort, startDateTime, endDateTime, filter));
    }

    @GetMapping("/ids")
    @Operation(summary = "根据ID获取供应商船舶类型列表")
    public Response<List<BaseBoatTypesVO>> getBoatTypeByIds(@RequestParam String ids) {
        return Response.success("根据ID获取供应商船舶类型列表成功", boatTypesService.vendorGetBoatTypeByIds(ids));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取供应商船舶类型详情")
    public Response<BaseBoatTypesVO> getBoatType(@PathVariable Long id) {
        return Response.success("获取供应商船舶类型详情成功", boatTypesService.vendorGetBoatTypeByIds(id.toString()).get(0));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新供应商船舶类型")
    public Response<BaseBoatTypesVO> updateBoatType(@PathVariable Long id, @RequestBody BaseBoatTypesDTO baseBoatTypeDTO) {
        return Response.success("更新供应商船舶类型成功", boatTypesService.vendorUpdateBoatType(id, baseBoatTypeDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除供应商船舶类型")
    public Response<String> deleteBoatType(@PathVariable Long id) {
        boatTypesService.vendorDeleteBoatType(id);
        return Response.success("删除供应商船舶类型成功");
    }

    @PostMapping("/")
    @Operation(summary = "创建供应商船舶类型")
    public Response<BaseBoatTypesVO> createBoatType(@RequestBody BaseBoatTypesDTO baseBoatTypeDTO) {
        return Response.success("创建供应商船舶类型成功", boatTypesService.vendorCreateBoatType(baseBoatTypeDTO));
    }
}
