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
@RequestMapping("/admin/boat/type")
@Tag(name = "AdminBoatType", description = "船舶类型模块")
@RequiredArgsConstructor
public class AdminBoatTypeController {

    private final BoatTypesService boatTypesService;

    @PostMapping("/list")
    @Operation(summary = "获取船舶类型列表")
    public Response<List<BaseBoatTypesVO>> getBoatTypeList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseBoatTypesDTO filter) {
        return Response.success("获取船舶类型列表成功", 
                boatTypesService.adminGetBoatTypeList(search, sort, startDateTime, endDateTime, filter));
    }

    @PostMapping("/page")
    @Operation(summary = "分页获取船舶类型列表")
    public Response<Page<BaseBoatTypesVO>> getBoatTypePage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseBoatTypesDTO filter) {
        return Response.success("获取船舶类型列表分页成功", 
                boatTypesService.adminGetBoatTypePage(pageNum, pageSize, search, sort, startDateTime, endDateTime, filter));
    }

    @GetMapping("/ids")
    @Operation(summary = "根据 ID 获取船舶类型列表")
    public Response<List<BaseBoatTypesVO>> getBoatTypeByIds(@RequestParam String ids) {
        return Response.success("根据 ID 获取船舶类型列表成功", boatTypesService.adminGetBoatTypeByIds(ids));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取船舶类型详情")
    public Response<BaseBoatTypesVO> getBoatType(@PathVariable Long id) {
        return Response.success("获取船舶类型详情成功", boatTypesService.adminGetBoatTypeByIds(id.toString()).get(0));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新船舶类型")
    public Response<BaseBoatTypesVO> updateBoatType(@PathVariable Long id, @RequestBody BaseBoatTypesDTO baseBoatTypeDTO) {
        return Response.success("更新船舶类型成功", boatTypesService.adminUpdateBoatType(id, baseBoatTypeDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除船舶类型")
    public Response<String> deleteBoatType(@PathVariable Long id) {
        boatTypesService.adminDeleteBoatType(id);
        return Response.success("删除船舶类型成功");
    }

    @PostMapping("/")
    @Operation(summary = "创建船舶类型")
    public Response<BaseBoatTypesVO> createBoatType(@RequestBody BaseBoatTypesDTO baseBoatTypeDTO) {
        return Response.success("创建船舶类型成功", boatTypesService.adminCreateBoatType(baseBoatTypeDTO));
    }
}
