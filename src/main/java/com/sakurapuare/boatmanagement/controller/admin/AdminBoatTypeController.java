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
@Tag(name = "管理员-船舶类型模块", description = "船舶类型模块")
@RequiredArgsConstructor
public class AdminBoatTypeController {

    private final BoatTypesService boatTypesService;

    @GetMapping("/list")
    @Operation(summary = "获取船舶类型列表")
    public Response<List<BaseBoatTypesVO>> list(@RequestParam BaseBoatTypesDTO queryDTO) {
        return Response.success("获取船舶类型列表成功", boatTypesService.getListQuery(queryDTO));
    }

    @GetMapping("/page")
    @Operation(summary = "获取船舶类型列表分页")
    public Response<Page<BaseBoatTypesVO>> listPage(@RequestParam(defaultValue = "1") Integer page,
                                                    @RequestParam(defaultValue = "10") Integer size, @RequestParam BaseBoatTypesDTO queryDTO) {
        return Response.success("获取船舶类型列表分页成功", boatTypesService.getPageQuery(page, size, queryDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取船舶类型详情")
    public Response<BaseBoatTypesVO> get(@PathVariable Long id) {
        BoatTypes boatTypes = boatTypesService.getById(id);
        BaseBoatTypesVO boatTypesVO = new BaseBoatTypesVO();
        BeanUtils.copyProperties(boatTypes, boatTypesVO);
        return Response.success("获取船舶类型详情成功", boatTypesVO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新船舶类型")
    public Response<String> update(@PathVariable Long id, @RequestBody BaseBoatTypesDTO baseBoatTypeDTO) {
        boatTypesService.updateBoatType(id, baseBoatTypeDTO);
        return Response.success("更新船舶类型成功");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除船舶类型")
    public Response<String> delete(@PathVariable Long id) {
        boatTypesService.deleteBoatType(id);
        return Response.success("删除船舶类型成功");
    }

    @PostMapping("/")
    @Operation(summary = "创建船舶类型")
    public Response<String> create(@RequestBody BaseBoatTypesDTO baseBoatTypeDTO) {
        boatTypesService.addBoatType(baseBoatTypeDTO);
        return Response.success("创建船舶类型成功");
    }
}
