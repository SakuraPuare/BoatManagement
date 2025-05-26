package com.sakurapuare.boatmanagement.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseUnitsDTO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseUnitsVO;
import com.sakurapuare.boatmanagement.service.UnitsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/unit")
@Tag(name = "AdminUnit", description = "管理员单位管理模块")
@RequiredArgsConstructor
public class AdminUnitController {

    private final UnitsService unitsService;

    @PostMapping("/list")
    @Operation(summary = "获取单位列表")
    public Response<List<BaseUnitsVO>> adminGetUnitList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseUnitsDTO filter) {
        return Response.success("获取单位列表成功", unitsService.adminGetUnitList(search, sort, startDateTime, endDateTime, filter));
    }

    @PostMapping("/page")
    @Operation(summary = "分页获取单位列表")
    public Response<Page<BaseUnitsVO>> adminGetUnitPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseUnitsDTO filter) {
        return Response.success("获取单位列表分页成功", 
                unitsService.adminGetUnitPage(pageNum, pageSize, search, sort, startDateTime, endDateTime, filter));
    }

    @GetMapping("/ids")
    @Operation(summary = "根据 ID 获取单位列表")
    public Response<List<BaseUnitsVO>> adminGetUnitByIds(@RequestParam String ids) {
        return Response.success("根据 ID 获取单位列表成功", unitsService.adminGetUnitByIds(ids));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取单位详情")
    public Response<BaseUnitsVO> adminGetUnit(@PathVariable Long id) {
        return Response.success("获取单位详情成功", unitsService.adminGetUnitByIds(id.toString()).get(0));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新单位")
    public Response<BaseUnitsVO> adminUpdateUnit(@PathVariable Long id, @RequestBody BaseUnitsDTO baseUnitsDTO) {
        return Response.success("更新单位成功", unitsService.adminUpdateUnit(id, baseUnitsDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除单位")
    public Response<String> adminDeleteUnit(@PathVariable Long id) {
        unitsService.adminDeleteUnit(id);
        return Response.success("删除单位成功");
    }

    @PostMapping("/")
    @Operation(summary = "创建单位")
    public Response<BaseUnitsVO> adminCreateUnit(@RequestBody BaseUnitsDTO baseUnitsDTO) {
        return Response.success("创建单位成功", unitsService.adminCreateUnit(baseUnitsDTO));
    }
}
