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
@Tag(name = "UserDock", description = "用户码头模块")
@RequiredArgsConstructor
public class UserDockController {

    private final DocksService docksService;

    @GetMapping("/list")
    @Operation(summary = "获取用户码头列表")
    public Response<List<BaseDocksVO>> userGetDockList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseDocksDTO filter) {
        return Response.success(docksService.userGetDockList(search, sort, startDateTime, endDateTime, filter));
    }

    @GetMapping("/page")
    @Operation(summary = "分页获取用户码头列表")
    public Response<Page<BaseDocksVO>> userGetDockPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseDocksDTO filter) {
        return Response.success(docksService.userGetDockPage(pageNum, pageSize, search, sort, startDateTime, endDateTime, filter));
    }

    @GetMapping("/ids/{ids}")
    @Operation(summary = "根据ID获取用户码头列表")
    public Response<List<BaseDocksVO>> userGetDockByIds(@PathVariable String ids) {
        return Response.success(docksService.userGetDockByIds(ids));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取用户码头详情")
    public Response<BaseDocksVO> userGetDock(@PathVariable Long id) {
        List<BaseDocksVO> docks = docksService.userGetDockByIds(id.toString());
        if (docks.isEmpty()) {
            return Response.error("码头不存在");
        }
        return Response.success(docks.get(0));
    }
}
