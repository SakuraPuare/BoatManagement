package com.sakurapuare.boatmanagement.controller.user;

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
@RequestMapping("/user/boat/request")
@Tag(name = "UserBoatRequest", description = "用户船只请求模块")
@RequiredArgsConstructor
public class UserBoatRequestController {

    private final BoatRequestsService boatRequestsService;

    @GetMapping("/list")
    @Operation(summary = "获取用户船只请求列表")
    public Response<List<BaseBoatRequestsVO>> userGetBoatRequestList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseBoatRequestsDTO filter) {
        return Response.success(boatRequestsService.userGetBoatRequestList(search, sort, startDateTime, endDateTime, filter));
    }

    @GetMapping("/page")
    @Operation(summary = "获取用户船只请求列表分页")
    public Response<Page<BaseBoatRequestsVO>> userGetBoatRequestPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseBoatRequestsDTO filter) {
        return Response.success(boatRequestsService.userGetBoatRequestPage(pageNum, pageSize, search, sort, startDateTime, endDateTime, filter));
    }

    @GetMapping("/ids/{ids}")
    @Operation(summary = "根据ID获取用户船只请求")
    public Response<List<BaseBoatRequestsVO>> userGetBoatRequestByIds(@PathVariable String ids) {
        return Response.success(boatRequestsService.userGetBoatRequestByIds(ids));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取用户船只请求详情")
    public Response<BaseBoatRequestsVO> userGetBoatRequest(@PathVariable Long id) {
        List<BaseBoatRequestsVO> requests = boatRequestsService.userGetBoatRequestByIds(id.toString());
        if (requests.isEmpty()) {
            return Response.error("船只请求不存在");
        }
        return Response.success(requests.get(0));
    }

    @PostMapping
    @Operation(summary = "创建用户船只请求")
    public Response<BaseBoatRequestsVO> userCreateBoatRequest(@RequestBody BaseBoatRequestsDTO dto) {
        return Response.success(boatRequestsService.userCreateBoatRequest(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新用户船只请求")
    public Response<BaseBoatRequestsVO> userUpdateBoatRequest(
            @PathVariable Long id,
            @RequestBody BaseBoatRequestsDTO dto) {
        return Response.success(boatRequestsService.userUpdateBoatRequest(id, dto));
    }

    @PutMapping("/cancel/{id}")
    @Operation(summary = "取消用户船只请求")
    public Response<BaseBoatRequestsVO> userCancelBoatRequest(@PathVariable Long id) {
        return Response.success(boatRequestsService.userCancelBoatRequest(id));
    }
}
