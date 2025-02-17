package com.sakurapuare.boatmanagement.controller.user;

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
@RequestMapping("/user/dock")
@Tag(name = "UserDockController", description = "用户码头管理")
@RequiredArgsConstructor
public class UserDockController {

    private final DocksService docksService;

    @PostMapping("/list")
    @Operation(summary = "获取用户码头列表", description = "获取用户码头列表")
    public Response<List<BaseDocksVO>> getUserDockListQuery(@RequestBody BaseDocksDTO queryDTO) {
        return Response.success(docksService.getUserDockListQuery(queryDTO));
    }

    @PostMapping("/page")
    @Operation(summary = "获取用户码头分页列表", description = "获取用户码头分页列表")
    public Response<Page<BaseDocksVO>> getUserDockPageQuery(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestBody BaseDocksDTO queryDTO) {
        return Response.success(docksService.getUserDockPageQuery(pageNum, pageSize, queryDTO));
    }
}
