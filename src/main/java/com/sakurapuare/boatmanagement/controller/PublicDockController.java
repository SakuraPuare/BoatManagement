package com.sakurapuare.boatmanagement.controller;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseDocksDTO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseDocksVO;
import com.sakurapuare.boatmanagement.service.DocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public/dock")
@Tag(name = "PublicDock", description = "公共码头模块")
@RequiredArgsConstructor
public class PublicDockController {

    private final DocksService docksService;

    @PostMapping("/list")
    @Operation(summary = "获取码头列表")
    public Response<List<BaseDocksVO>> getPublicDockList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseDocksDTO filter) {
        return Response.success("获取码头列表成功", 
                docksService.publicGetDockList(search, sort, startDateTime, endDateTime, filter));
    }

    @PostMapping("/page")
    @Operation(summary = "分页获取码头列表")
    public Response<Page<BaseDocksVO>> getPublicDockPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseDocksDTO filter) {
        return Response.success("获取码头列表分页成功", 
                docksService.publicGetDockPage(pageNum, pageSize, search, sort, startDateTime, endDateTime, filter));
    }

    @GetMapping("/ids")
    @Operation(summary = "根据 ID 获取码头列表")
    public Response<List<BaseDocksVO>> getPublicDockByIds(@RequestParam String ids) {
        return Response.success("根据 ID 获取码头列表成功", docksService.publicGetDockByIds(ids));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取码头详情")
    public Response<BaseDocksVO> getPublicDock(@PathVariable Long id) {
        return Response.success("获取码头详情成功", docksService.publicGetDockByIds(id.toString()).get(0));
    }
} 