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
@Tag(name = "AdminUnit", description = "单位模块")
@RequiredArgsConstructor
public class AdminUnitController {

    private final UnitsService unitsService;

    @PostMapping("/list")
    @Operation(summary = "获取单位列表")
    public Response<List<BaseUnitsVO>> getAdminUnitListQuery(@RequestBody BaseUnitsDTO queryDTO) {
        return Response.success("获取单位列表成功", unitsService.getAdminUnitListQuery(queryDTO));
    }

    @PostMapping("/page")
    @Operation(summary = "获取单位列表分页")
    public Response<Page<BaseUnitsVO>> getAdminUnitPageQuery(@RequestParam(defaultValue = "1") Integer pageNum,
                                                             @RequestParam(defaultValue = "10") Integer pageSize, @RequestBody BaseUnitsDTO queryDTO) {
        return Response.success("获取单位列表分页成功", unitsService.getAdminUnitPageQuery(pageNum, pageSize, queryDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取单位详情")
    public Response<BaseUnitsVO> getAdminUnit(@PathVariable Long id) {
        return Response.success("获取单位详情成功", unitsService.getAdminUnit(id));
    }
}
