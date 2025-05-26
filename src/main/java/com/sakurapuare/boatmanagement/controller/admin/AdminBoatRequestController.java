package com.sakurapuare.boatmanagement.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseBoatRequestsDTO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseBoatRequestsVO;
import com.sakurapuare.boatmanagement.service.BoatRequestsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/boat/request")
@Tag(name = "AdminBoatRequest", description = "管理员船只请求管理模块")
@RequiredArgsConstructor
public class AdminBoatRequestController {

    private final BoatRequestsService boatRequestsService;

    @PostMapping("/list")
    @Operation(summary = "获取船只请求列表")
    public Response<List<BaseBoatRequestsVO>> adminGetBoatRequestList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseBoatRequestsDTO filter) {
        return Response.success("获取船只请求列表成功", 
                boatRequestsService.adminGetBoatRequestList(search, sort, startDateTime, endDateTime, filter));
    }

    @PostMapping("/page")
    @Operation(summary = "分页获取船只请求列表")
    public Response<Page<BaseBoatRequestsVO>> adminGetBoatRequestPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseBoatRequestsDTO filter) {
        return Response.success("获取船只请求列表分页成功", 
                boatRequestsService.adminGetBoatRequestPage(pageNum, pageSize, search, sort, startDateTime, endDateTime, filter));
    }

    @GetMapping("/ids")
    @Operation(summary = "根据 ID 获取船只请求列表")
    public Response<List<BaseBoatRequestsVO>> adminGetBoatRequestByIds(@RequestParam String ids) {
        return Response.success("根据 ID 获取船只请求列表成功", boatRequestsService.adminGetBoatRequestByIds(ids));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取船只请求详情")
    public Response<BaseBoatRequestsVO> adminGetBoatRequest(@PathVariable Long id) {
        return Response.success("获取船只请求详情成功", boatRequestsService.adminGetBoatRequestByIds(id.toString()).get(0));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新船只请求")
    public Response<BaseBoatRequestsVO> adminUpdateBoatRequest(@PathVariable Long id, @RequestBody BaseBoatRequestsDTO baseBoatRequestsDTO) {
        return Response.success("更新船只请求成功", boatRequestsService.adminUpdateBoatRequest(id, baseBoatRequestsDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除船只请求")
    public Response<String> adminDeleteBoatRequest(@PathVariable Long id) {
        boatRequestsService.adminDeleteBoatRequest(id);
        return Response.success("删除船只请求成功");
    }

    @PostMapping("/")
    @Operation(summary = "创建船只请求")
    public Response<BaseBoatRequestsVO> adminCreateBoatRequest(@RequestBody BaseBoatRequestsDTO baseBoatRequestsDTO) {
        return Response.success("创建船只请求成功", boatRequestsService.adminCreateBoatRequest(baseBoatRequestsDTO));
    }
} 