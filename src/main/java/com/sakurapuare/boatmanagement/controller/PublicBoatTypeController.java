package com.sakurapuare.boatmanagement.controller;

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
@RequestMapping("/public/boat-type")
@Tag(name = "PublicBoatType", description = "公共船艇类型模块")
@RequiredArgsConstructor
public class PublicBoatTypeController {

    private final BoatTypesService boatTypesService;

    @PostMapping("/list")
    @Operation(summary = "获取船艇类型列表")
    public Response<List<BaseBoatTypesVO>> getPublicBoatTypeList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseBoatTypesDTO filter) {
        return Response.success("获取船艇类型列表成功", 
                boatTypesService.publicGetBoatTypeList(search, sort, startDateTime, endDateTime, filter));
    }

    @PostMapping("/page")
    @Operation(summary = "分页获取船艇类型列表")
    public Response<Page<BaseBoatTypesVO>> getPublicBoatTypePage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseBoatTypesDTO filter) {
        return Response.success("获取船艇类型列表分页成功", 
                boatTypesService.publicGetBoatTypePage(pageNum, pageSize, search, sort, startDateTime, endDateTime, filter));
    }

    @GetMapping("/ids")
    @Operation(summary = "根据 ID 获取船艇类型列表")
    public Response<List<BaseBoatTypesVO>> getPublicBoatTypeByIds(@RequestParam String ids) {
        return Response.success("根据 ID 获取船艇类型列表成功", boatTypesService.publicGetBoatTypeByIds(ids));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取船艇类型详情")
    public Response<BaseBoatTypesVO> getPublicBoatType(@PathVariable Long id) {
        return Response.success("获取船艇类型详情成功", boatTypesService.publicGetBoatTypeByIds(id.toString()).get(0));
    }
} 