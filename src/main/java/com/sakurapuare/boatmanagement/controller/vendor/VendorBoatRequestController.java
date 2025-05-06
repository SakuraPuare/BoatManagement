package com.sakurapuare.boatmanagement.controller.vendor;

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
@RequestMapping("/vendor/boat/request")
@Tag(name = "VendorBoatRequest", description = "商家船只请求模块")
@RequiredArgsConstructor
public class VendorBoatRequestController {

    private final BoatRequestsService boatRequestsService;

    @PostMapping("/list")
    @Operation(summary = "获取商家船只请求列表")
    public Response<List<BaseBoatRequestsVO>> getVendorBoatRequestsListQuery(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseBoatRequestsDTO filter) {
        return Response.success("获取商家船只请求列表成功", 
                boatRequestsService.vendorGetBoatRequestList(search, sort, startDateTime, endDateTime, filter));
    }

    @PostMapping("/page")
    @Operation(summary = "获取商家船只请求列表分页")
    public Response<Page<BaseBoatRequestsVO>> getVendorBoatRequestsPageQuery(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseBoatRequestsDTO filter) {
        return Response.success("获取商家船只请求列表分页成功", 
                boatRequestsService.vendorGetBoatRequestPage(pageNum, pageSize, search, sort, startDateTime, endDateTime, filter));
    }
    
    @GetMapping("/ids")
    @Operation(summary = "根据ID获取商家船只请求")
    public Response<List<BaseBoatRequestsVO>> vendorGetBoatRequestByIds(@RequestParam String ids) {
        return Response.success("根据ID获取商家船只请求成功", boatRequestsService.vendorGetBoatRequestByIds(ids));
    }
    
    @PutMapping("/status/{id}")
    @Operation(summary = "更新商家船只请求状态")
    public Response<BaseBoatRequestsVO> vendorUpdateBoatRequestStatus(
            @PathVariable Long id, 
            @RequestParam String status) {
        return Response.success("更新商家船只请求状态成功", boatRequestsService.vendorUpdateBoatRequestStatus(id, status));
    }
}
