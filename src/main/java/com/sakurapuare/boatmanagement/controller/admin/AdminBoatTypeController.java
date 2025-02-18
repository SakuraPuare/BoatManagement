package com.sakurapuare.boatmanagement.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseBoatTypesDTO;
import com.sakurapuare.boatmanagement.pojo.entity.BoatTypes;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseBoatTypesVO;
import com.sakurapuare.boatmanagement.service.BoatTypesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/boat-type")
@Tag(name = "AdminBoatType", description = "船舶类型模块")
@RequiredArgsConstructor
public class AdminBoatTypeController {

    private final BoatTypesService boatTypesService;

    @PostMapping("/list")
    @Operation(summary = "获取船舶类型列表")
    public Response<List<BaseBoatTypesVO>> getAdminBoatTypeListQuery(@RequestBody BaseBoatTypesDTO queryDTO) {
        return Response.success("获取船舶类型列表成功", boatTypesService.getAdminBoatTypeListQuery(queryDTO));
    }

    @PostMapping("/page")
    @Operation(summary = "获取船舶类型列表分页")
    public Response<Page<BaseBoatTypesVO>> getAdminBoatTypePageQuery(@RequestParam(defaultValue = "1") Integer pageNum,
                                                                     @RequestParam(defaultValue = "10") Integer pageSize, @RequestBody BaseBoatTypesDTO queryDTO) {
        return Response.success("获取船舶类型列表分页成功", boatTypesService.getAdminBoatTypePageQuery(pageNum, pageSize, queryDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取船舶类型详情")
    public Response<BaseBoatTypesVO> getAdminBoatType(@PathVariable Long id) {
        BoatTypes boatTypes = boatTypesService.getById(id);
        BaseBoatTypesVO boatTypesVO = new BaseBoatTypesVO();
        BeanUtils.copyProperties(boatTypes, boatTypesVO);
        return Response.success("获取船舶类型详情成功", boatTypesVO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新船舶类型")
    public Response<String> updateAdminBoatType(@PathVariable Long id, @RequestBody BaseBoatTypesDTO baseBoatTypeDTO) {
        boatTypesService.updateAdminBoatType(id, baseBoatTypeDTO);
        return Response.success("更新船舶类型成功");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除船舶类型")
    public Response<String> deleteAdminBoatType(@PathVariable Long id) {
        boatTypesService.deleteAdminBoatType(id);
        return Response.success("删除船舶类型成功");
    }

    @PostMapping("/")
    @Operation(summary = "创建船舶类型")
    public Response<String> createAdminBoatType(@RequestBody BaseBoatTypesDTO baseBoatTypeDTO) {
        boatTypesService.createAdminBoatType(baseBoatTypeDTO);
        return Response.success("创建船舶类型成功");
    }
}
