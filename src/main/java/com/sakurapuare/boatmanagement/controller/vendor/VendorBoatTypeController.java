package com.sakurapuare.boatmanagement.controller.vendor;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseBoatTypesDTO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseBoatTypesVO;
import com.sakurapuare.boatmanagement.service.BoatTypesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
    public Response<List<BaseBoatTypesVO>> getVendorBoatTypes(@RequestBody BaseBoatTypesDTO queryDTO) {
        return Response.success(boatTypesService.getVendorBoatTypesList(queryDTO));
    }

    @PostMapping("/page")
    @Operation(summary = "获取供应商船舶列表分页")
    public Response<Page<BaseBoatTypesVO>> getVendorBoatTypesPage(@RequestParam Integer pageNum,
                                                                  @RequestBody Integer pageSize, @RequestParam BaseBoatTypesDTO queryDTO) {
        return Response.success(boatTypesService.getVendorBoatTypesPage(pageNum, pageSize, queryDTO));
    }

    @PostMapping("/")
    @Operation(summary = "添加供应商船舶")
    public Response<String> addVendorBoatType(@RequestBody BaseBoatTypesDTO boatTypesDTO) {
        boatTypesService.addVendorBoatType(boatTypesDTO);
        return Response.success("添加船舶类型成功");
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新供应商船舶")
    public Response<String> updateVendorBoatType(@PathVariable Long id, @RequestBody BaseBoatTypesDTO boatTypesDTO) {
        boatTypesService.updateVendorBoatType(id, boatTypesDTO);
        return Response.success("更新船舶类型成功");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除供应商船舶")
    public Response<String> deleteVendorBoatType(@PathVariable Long id) {
        boatTypesService.deleteVendorBoatType(id);
        return Response.success("删除船舶类型成功");
    }
}
