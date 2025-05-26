package com.sakurapuare.boatmanagement.controller.admin;

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
@RequestMapping("/admin/boat-type")
@Tag(name = "AdminBoatType", description = "管理员船艇类型管理模块")
@RequiredArgsConstructor
public class AdminBoatTypeController {

    private final BoatTypesService boatTypesService;

    @PostMapping("/list")
    @Operation(summary = "获取船艇类型列表")
    public Response<List<BaseBoatTypesVO>> adminGetBoatTypeList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseBoatTypesDTO filter) {
        return Response.success("获取船艇类型列表成功", 
                boatTypesService.adminGetBoatTypeList(search, sort, startDateTime, endDateTime, filter));
    }

    @PostMapping("/page")
    @Operation(summary = "分页获取船艇类型列表")
    public Response<Page<BaseBoatTypesVO>> adminGetBoatTypePage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseBoatTypesDTO filter) {
        return Response.success("获取船艇类型列表分页成功", 
                boatTypesService.adminGetBoatTypePage(pageNum, pageSize, search, sort, startDateTime, endDateTime, filter));
    }

    @GetMapping("/ids")
    @Operation(summary = "根据 ID 获取船艇类型列表")
    public Response<List<BaseBoatTypesVO>> adminGetBoatTypeByIds(@RequestParam String ids) {
        return Response.success("根据 ID 获取船艇类型列表成功", boatTypesService.adminGetBoatTypeByIds(ids));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取船艇类型详情")
    public Response<BaseBoatTypesVO> adminGetBoatType(@PathVariable Long id) {
        return Response.success("获取船艇类型详情成功", boatTypesService.adminGetBoatTypeByIds(id.toString()).get(0));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新船艇类型")
    public Response<BaseBoatTypesVO> adminUpdateBoatType(@PathVariable Long id, @RequestBody BaseBoatTypesDTO baseBoatTypesDTO) {
        return Response.success("更新船艇类型成功", boatTypesService.adminUpdateBoatType(id, baseBoatTypesDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除船艇类型")
    public Response<String> adminDeleteBoatType(@PathVariable Long id) {
        boatTypesService.adminDeleteBoatType(id);
        return Response.success("删除船艇类型成功");
    }

    @PostMapping("/")
    @Operation(summary = "创建船艇类型")
    public Response<BaseBoatTypesVO> adminCreateBoatType(@RequestBody BaseBoatTypesDTO baseBoatTypesDTO) {
        return Response.success("创建船艇类型成功", boatTypesService.adminCreateBoatType(baseBoatTypesDTO));
    }
}
