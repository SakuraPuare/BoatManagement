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

    @PostMapping("/list")
    @Operation(summary = "获取用户船只请求列表", description = "获取用户船只请求列表")
    public Response<List<BaseBoatRequestsVO>> getUserBoatRequestsListQuery(
            @RequestBody BaseBoatRequestsDTO boatRequestDTO) {
        return Response.success("获取用户船只请求列表成功", boatRequestsService.getUserBoatRequestsListQuery(boatRequestDTO));
    }

    @PostMapping("/page")
    @Operation(summary = "获取用户船只请求列表分页", description = "获取用户船只请求列表分页")
    public Response<Page<BaseBoatRequestsVO>> getUserBoatRequestsPageQuery(
            @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestBody BaseBoatRequestsDTO boatRequestDTO) {
        return Response.success("获取用户船只请求列表分页成功", boatRequestsService.getUserBoatRequestsPageQuery(pageNum, pageSize, boatRequestDTO));
    }

    @PostMapping("/")
    @Operation(summary = "创建用户船只请求", description = "创建用户船只请求")
    public Response<String> createUserBoatRequest(@RequestBody BaseBoatRequestsDTO boatRequestDTO) {
        boatRequestsService.createUserBoatRequest(boatRequestDTO);
        return Response.success("创建用户船只请求成功");
    }

    @PostMapping("/cancel/{id}")
    @Operation(summary = "取消用户船只请求", description = "取消用户船只请求")
    public Response<String> cancelUserBoatRequest(@PathVariable Long id) {
        boatRequestsService.cancelUserBoatRequest(id);
        return Response.success("取消用户船只请求成功");
    }

    @PostMapping("/update/{id}")
    @Operation(summary = "更新用户船只请求", description = "更新用户船只请求")
    public Response<String> updateUserBoatRequest(@PathVariable Long id,
                                                  @RequestBody BaseBoatRequestsDTO boatRequestDTO) {
        boatRequestsService.updateUserBoatRequest(id, boatRequestDTO);
        return Response.success("更新用户船只请求成功");
    }
}
