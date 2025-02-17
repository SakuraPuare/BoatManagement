package com.sakurapuare.boatmanagement.controller.vendor;

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
@RequestMapping("/vendor/dock")
@Tag(name = "VendorDockController", description = "供应商码头管理")
@RequiredArgsConstructor
public class VendorDockController {

    private final DocksService docksService;

    @GetMapping("/list")
    @Operation(summary = "获取供应商码头列表", description = "获取供应商码头列表")
    public Response<List<BaseDocksVO>> getVendorDockListQuery(@RequestBody BaseDocksDTO queryDTO) {
        return Response.success(docksService.getVendorDockListQuery(queryDTO));
    }

    @GetMapping("/page")
    @Operation(summary = "获取供应商码头分页列表", description = "获取供应商码头分页列表")
    public Response<Page<BaseDocksVO>> getVendorDockPageQuery(@RequestParam(defaultValue = "1") Integer pageNum,
                                                              @RequestParam(defaultValue = "10") Integer pageSize, @RequestBody BaseDocksDTO queryDTO) {
        return Response.success(docksService.getVendorDockPageQuery(pageNum, pageSize, queryDTO));
    }

}
