package com.sakurapuare.boatmanagement.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseBoatTypesDTO;
import com.sakurapuare.boatmanagement.pojo.entity.BoatTypes;
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

    private final BoatTypesService boatTypeService;

    @GetMapping("/list")
    @Operation(summary = "获取船舶类型列表")
    public Response<List<BoatTypes>> list() {
        return Response.success("获取船舶类型列表成功", boatTypeService.list());
    }

    @GetMapping("/page")
    @Operation(summary = "获取船舶类型列表分页")
    public Response<Page<BoatTypes>> listPage(@RequestParam(defaultValue = "1") Integer page,
                                              @RequestParam(defaultValue = "10") Integer size) {
        return Response.success("获取船舶类型列表分页成功", boatTypeService.page(new Page<>(page, size)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取船舶类型详情")
    public Response<BoatTypes> get(@PathVariable Long id) {
        return Response.success("获取船舶类型详情成功", boatTypeService.getById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新船舶类型")
    public Response<BoatTypes> update(@PathVariable Long id, @RequestBody BaseBoatTypesDTO baseBoatTypeDTO) {
        BoatTypes boatTypes = new BoatTypes();
        BeanUtils.copyProperties(baseBoatTypeDTO, boatTypes);
        boatTypes.setId(id);
        boatTypeService.updateById(boatTypes);
        return Response.success("更新船舶类型成功", boatTypes);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除船舶类型")
    public Response<String> delete(@PathVariable Long id) {
        boatTypeService.removeById(id);
        return Response.success("删除船舶类型成功");
    }

    @PostMapping("/")
    @Operation(summary = "创建船舶类型")
    public Response<BoatTypes> create(@RequestBody BaseBoatTypesDTO baseBoatTypeDTO) {
        BoatTypes boatTypes = new BoatTypes();
        BeanUtils.copyProperties(baseBoatTypeDTO, boatTypes);
        boatTypeService.save(boatTypes);
        return Response.success("创建船舶类型成功", boatTypes);
    }
}
