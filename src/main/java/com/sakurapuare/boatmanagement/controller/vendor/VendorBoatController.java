package com.sakurapuare.boatmanagement.controller.vendor;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseBoatsDTO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseBoatsVO;
import com.sakurapuare.boatmanagement.service.BoatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
    public Response<List<BaseBoatsVO>> getVendorBoats(@RequestBody BaseBoatsDTO queryDTO) {
        return Response.success(boatsService.getVendorBoatsList(queryDTO));
    }

    @PostMapping("/page")
    @Operation(summary = "获取供应商船舶列表分页")
    public Response<Page<BaseBoatsVO>> getVendorBoatsPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                                          @RequestBody Integer pageSize, @RequestParam BaseBoatsDTO queryDTO) {
        return Response.success(boatsService.getVendorBoatsPage(pageNum, pageSize, queryDTO));
    }

    @PostMapping("/")
    @Operation(summary = "添加供应商船舶")
    public Response<String> addVendorBoat(@RequestBody BaseBoatsDTO boatsDTO) {
        boatsService.addVendorBoat(boatsDTO);
        return Response.success("添加船舶成功");
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新供应商船舶")
    public Response<String> updateVendorBoat(@PathVariable Long id, @RequestBody BaseBoatsDTO boatsDTO) {
        boatsService.updateVendorBoat(id, boatsDTO);
        return Response.success("更新船舶成功");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除供应商船舶")
    public Response<String> deleteVendorBoat(@PathVariable Long id) {
        boatsService.deleteVendorBoat(id);
        return Response.success("删除船舶成功");
    }
}
