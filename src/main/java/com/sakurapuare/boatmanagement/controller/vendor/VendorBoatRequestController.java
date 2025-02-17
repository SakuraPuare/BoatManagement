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
            @RequestBody BaseBoatRequestsDTO baseBoatRequestsDTO) {
        return Response.success("获取商家船只请求列表成功", boatRequestsService.getVendorBoatRequestsListQuery(baseBoatRequestsDTO));
    }

    @PostMapping("/page")
    @Operation(summary = "获取商家船只请求列表分页")
    public Response<Page<BaseBoatRequestsVO>> getVendorBoatRequestsPageQuery(@RequestParam(defaultValue = "1") Integer pageNum,
                                                                             @RequestParam(defaultValue = "10") Integer pageSize, @RequestBody BaseBoatRequestsDTO baseBoatRequestsDTO) {
        return Response
                .success("获取商家船只请求列表分页成功", boatRequestsService.getVendorBoatRequestsPageQuery(pageNum, pageSize, baseBoatRequestsDTO));
    }
}
